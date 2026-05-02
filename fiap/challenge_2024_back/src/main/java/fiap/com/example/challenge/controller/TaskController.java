package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.model.Task;
import fiap.com.example.challenge.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/search")
    public List<String> searchTasks(@RequestParam String searchTerm) {
        return taskService.findTop3BySymptomOrDescription(searchTerm);
    }
}
