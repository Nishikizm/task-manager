package io.github.nishikizm.taskmanager.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.nishikizm.taskmanager.service.TaskService;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    
    @GetMapping("/tasks")
    public String get(Model model) {
        List<TaskResponse> tasks = service.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskForm", new TaskCreateForm("", "", 2026, 1, 1, "00:00", false));
        return "tasks";
    }

    @PostMapping("/tasks")
    public String create(@Valid @ModelAttribute TaskCreateForm form, BindingResult binding) {
        return "tasks";
    }
}
