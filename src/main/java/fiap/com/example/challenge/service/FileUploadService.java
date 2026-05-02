package fiap.com.example.challenge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUploadService {

    // Valid file extensions
    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList("txt", "pdf", "json", "docx");

    public void saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName == null || !isAllowedFile(fileName)) {
            throw new IOException("Invalid file type.");
        }

        // Ensure the upload directory exists
        String UPLOAD_DIR = "uploads/";
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file locally
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());
    }

    private boolean isAllowedFile(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return ""; // No extension found
        }
        return fileName.substring(dotIndex + 1);
    }
}
