package com.pidparser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main CLI application to parse process data from input file
 * and generate output files grouped by process name.
 */
public class PidParser {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar java-pid-parser.jar <input_file.txt>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        PidParser parser = new PidParser();
        
        try {
            parser.processFile(inputFilePath);
            System.out.println("Processing completed successfully!");
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Main processing method that reads input file, parses data,
     * aggregates by process and event, and writes output files.
     */
    public void processFile(String inputFilePath) throws IOException {
        Path inputPath = Paths.get(inputFilePath);
        
        if (!Files.exists(inputPath)) {
            throw new FileNotFoundException("Input file not found: " + inputFilePath);
        }

        // Read and parse all records
        List<ProcessRecord> records = readAndParseFile(inputPath);
        
        if (records.isEmpty()) {
            System.out.println("No records found in input file.");
            return;
        }

        // Group by process name, then by event
        Map<String, Map<String, List<ProcessRecord>>> groupedData = groupByProcessAndEvent(records);

        // Prepare directory names
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String inputFileName = inputPath.getFileName().toString();
        String inputBaseName = inputFileName.contains(".")
                ? inputFileName.substring(0, inputFileName.lastIndexOf('.'))
                : inputFileName;

        // Determine output directory (out/<inputFileName>/<timestamp> at project root)
        // Use current working directory as base, not input file's parent
        Path outRoot = Paths.get("out");
        Path outputDirectory = outRoot.resolve(inputBaseName).resolve(timestamp);
        Files.createDirectories(outputDirectory);

        // Generate output files
        generateOutputFiles(groupedData, outputDirectory);
    }

    /**
     * Reads the input file and parses each line into ProcessRecord objects.
     */
    private List<ProcessRecord> readAndParseFile(Path inputPath) throws IOException {
        List<ProcessRecord> records = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip empty lines and header
                if (line.isEmpty() || isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Parse pipe-delimited line
                String[] parts = line.split("\\|");
                
                if (parts.length >= 5) {
                    try {
                        String timestamp = parts[0].trim();
                        String event = parts[1].trim();
                        String uid = parts[2].trim();
                        String pid = parts[3].trim();
                        String processName = parts[4].trim();
                        
                        records.add(new ProcessRecord(timestamp, event, uid, pid, processName));
                    } catch (Exception e) {
                        System.err.println("Warning: Skipping invalid line: " + line);
                    }
                }
            }
        }
        
        return records;
    }

    /**
     * Groups records by process name, then by event.
     */
    private Map<String, Map<String, List<ProcessRecord>>> groupByProcessAndEvent(
            List<ProcessRecord> records) {
        
        return records.stream()
                .collect(Collectors.groupingBy(
                    ProcessRecord::getProcessName,
                    Collectors.groupingBy(ProcessRecord::getEvent)
                ));
    }

    /**
     * Generates output files for each process name.
     */
    private void generateOutputFiles(
            Map<String, Map<String, List<ProcessRecord>>> groupedData,
            Path outputDirectory) throws IOException {
        
        for (Map.Entry<String, Map<String, List<ProcessRecord>>> processEntry : groupedData.entrySet()) {
            String processName = processEntry.getKey();
            Map<String, List<ProcessRecord>> eventGroups = processEntry.getValue();
            
            // Create list of summaries for this process
            List<ProcessEventSummary> summaries = new ArrayList<>();
            
            for (Map.Entry<String, List<ProcessRecord>> eventEntry : eventGroups.entrySet()) {
                String event = eventEntry.getKey();
                List<ProcessRecord> records = eventEntry.getValue();
                
                // Get UID and PID from first record (they should be the same for same process)
                ProcessRecord firstRecord = records.get(0);
                String uid = firstRecord.getUid();
                String pid = firstRecord.getPid();
                
                // Find first timestamp (earliest)
                String firstTimestamp = records.stream()
                        .map(ProcessRecord::getTimestamp)
                        .min(Comparator.naturalOrder())
                        .orElse("");
                
                int counter = records.size();
                
                summaries.add(new ProcessEventSummary(event, uid, pid, processName, 
                                                      counter, firstTimestamp));
            }
            
            // Sort summaries by event (as shown in example)
            summaries.sort(Comparator.comparing(ProcessEventSummary::getEvent));
            
            // Write output file
            writeOutputFile(processName, summaries, outputDirectory);
        }
    }

    /**
     * Writes the output file for a specific process.
     */
    private void writeOutputFile(String processName, List<ProcessEventSummary> summaries,
                                Path outputDirectory) throws IOException {
        
        String fileName = processName + ".txt";
        Path outputPath = outputDirectory != null 
                ? outputDirectory.resolve(fileName)
                : Paths.get(fileName);
        
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            // Write header
            writer.write("EVENT| UID| PID| PROCESS_NAME | COUNTER | FIRST TIMESTAMP");
            writer.newLine();
            
            // Write data lines
            for (ProcessEventSummary summary : summaries) {
                writer.write(summary.toOutputLine());
                writer.newLine();
            }
        }
        
        System.out.println("Generated: " + outputPath.toString());
    }
}

