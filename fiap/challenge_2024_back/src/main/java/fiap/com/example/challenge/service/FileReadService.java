package fiap.com.example.challenge.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileReadService {

    public String readFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String extension = getFileExtension(path.getFileName().toString());

        return switch (extension) {
            case "txt" -> readTextFile(path);
            case "json" -> readJsonFile(path);
            case "docx" -> readDocxFile(path);
            case "pdf" -> readPdfFile(path);
            default -> throw new UnsupportedOperationException("File type not supported");
        };
    }

    private String readTextFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private String readJsonFile(Path path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object json = objectMapper.readValue(path.toFile(), Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private String readDocxFile(Path path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path.toFile());
             XWPFDocument document = new XWPFDocument(fis)) {
            StringBuilder text = new StringBuilder();
            document.getParagraphs().forEach(paragraph -> text.append(paragraph.getText()).append("\n"));
            return text.toString();
        }
    }

    private String readPdfFile(Path path) throws IOException {
        try (PDDocument document = PDDocument.load(path.toFile())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}

