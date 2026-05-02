package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gemini-api")
public class PythonController {

    private final PythonService pythonService;

    @Autowired
    public PythonController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @PostMapping("/submit")
    public String submitJson(@RequestBody Map<String, Object> jsonData) {
        return pythonService.submitService(jsonData);
    }
}
