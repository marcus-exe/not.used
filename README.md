# Java PID Parser

A Java CLI tool that parses process data from a pipe-delimited text file and generates separate output files grouped by process name.

## Features

- Reads pipe-delimited input files with process data
- Groups data by process name and event
- Calculates counters and first timestamps for each event
- Generates separate output files for each process inside `out/<input_file_name>/<timestamp>/`

## Input Format

The input file should be a pipe-delimited text file with the following columns:
```
TIMESTAMP| EVENT| UID| PID| PROCESS_NAME
```

Example:
```
TIMESTAMP| EVENT| UID| PID| PROCESS_NAME
1090019010| 122| 10300| 879| tiktok
1090019030| 332| 10331| 97| youtube
1090045044| 434| 10344| 65| chrome
```

## Output Format

For each process name, a separate `.txt` file is generated in a subdirectory named after the input file and timestamp (e.g., `out/processes/2025-11-10_09-37-32/`) with aggregated data:
```
EVENT| UID| PID| PROCESS_NAME | COUNTER | FIRST TIMESTAMP
```

## Data Processing

The tool processes records by grouping them first by process name, then by event. The internal data structure created during processing looks like this:

```
{
  "youtube" -> {
    "332" -> [record1, record2, record3],
    "265" -> [record4],
    "434" -> [record5]
  },
  "chrome" -> {
    "434" -> [record6, record7, record8]
  },
  "tiktok" -> {
    "122" -> [record9, record10]
  }
}
```

This nested structure allows the tool to:
- Group all records by their process name (first level)
- Within each process, group records by event type (second level)
- Count occurrences and find the earliest timestamp for each event/process combination

## Building the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build Command
```bash
mvn clean package
```

This will create a JAR file in the `target` directory: `java-pid-parser-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Usage

Place your input files in the `input/` folder, then run:

```bash
java -jar target/java-pid-parser-1.0-SNAPSHOT-jar-with-dependencies.jar input/<input_file.txt>
```

Or if running from source:
```bash
mvn compile exec:java -Dexec.mainClass="com.pidparser.PidParser" -Dexec.args="input/<input_file.txt>"
```

You can also specify the full path to any input file - the output will always be created in the `out/` directory at the project root.

## Example

Given an input file `input/A_sample_input.txt`:
```
TIMESTAMP| EVENT| UID| PID| PROCESS_NAME
1090019010| 122| 10300| 879| tiktok
1090019030| 332| 10331| 97| youtube
1090045044| 434| 10344| 65| chrome
1090059034| 332| 10331| 97| youtube
```

Running:
```bash
java -jar target/java-pid-parser-1.0-SNAPSHOT-jar-with-dependencies.jar input/A_sample_input.txt
```

The tool will generate a timestamped folder inside a directory matching the input file name (e.g., `out/A_sample_input/2025-11-10_10-22-27/`) containing:
- `out/A_sample_input/2025-11-10_10-22-27/tiktok.txt`
- `out/A_sample_input/2025-11-10_10-22-27/youtube.txt`
- `out/A_sample_input/2025-11-10_10-22-27/chrome.txt`

Each run creates a new timestamped folder, allowing you to keep multiple runs organized. Each file contains aggregated data grouped by event.

## Project Structure

```
java.pid.parser/
├── pom.xml
├── README.md
├── input/                                    # Input files directory
│   ├── A_sample_input.txt
│   ├── B_sample_input.txt
│   └── C_sample_input.txt
├── out/                                      # Generated output files (gitignored)
│   └── <input_file_name>/
│       └── <timestamp>/
│           ├── process1.txt
│           └── process2.txt
└── src/
    └── main/
        └── java/
            └── com/
                └── pidparser/
                    ├── PidParser.java          # Main application class
                    ├── ProcessRecord.java      # Data model for input records
                    └── ProcessEventSummary.java # Data model for aggregated output
```

