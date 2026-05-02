package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.service.FileReadService;
import fiap.com.example.challenge.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class UploadAndReadFileController {

    private final FileUploadService fileUploadService;
    private final FileReadService fileReadService;

    @Autowired
    public UploadAndReadFileController(FileUploadService fileUploadService, FileReadService fileReadService) {
        this.fileUploadService = fileUploadService;
        this.fileReadService = fileReadService;
    }

    @PostMapping("/upload-and-read")
    public ResponseEntity<String> uploadAndTriggerRead(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file using FileUploadService
            fileUploadService.saveFile(file);

            // Get the file path
            String filePath = Paths.get("uploads", file.getOriginalFilename()).toString();

            // Trigger file reading after successful upload
            String content = fileReadService.readFileContent(filePath);

            return ResponseEntity.ok("File uploaded successfully. File content: " + content);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
