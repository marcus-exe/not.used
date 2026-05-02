package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.service.FileReadService;
import fiap.com.example.challenge.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadService fileUploadService;
    private final FileReadService fileReadService;

    @Autowired
    public FileController(FileUploadService fileUploadService, FileReadService fileReadService) {
        this.fileUploadService = fileUploadService;
        this.fileReadService = fileReadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileUploadService.saveFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity<String> readFileContent(@RequestParam("filePath") String filePath) {
        try {
            String content = fileReadService.readFileContent(filePath);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
