package io.github.nishikizm.taskmanager.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.nishikizm.taskmanager.service.TaskService;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    
    @GetMapping("/tasks")
    public String test(Model model) {
        List<TaskResponse> tasks = service.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
