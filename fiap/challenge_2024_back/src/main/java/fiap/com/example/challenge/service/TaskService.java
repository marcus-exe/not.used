package fiap.com.example.challenge.service;

import fiap.com.example.challenge.model.Task;
import fiap.com.example.challenge.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<String> findTop3BySymptomOrDescription(String searchTerm) {
        List<String> resolutions = taskRepository.findTop3BySymptomOrDescription(searchTerm);
        if (resolutions.isEmpty()) {
            resolutions = taskRepository.findRandomResolutions();
        }
        return resolutions;
    }

}
