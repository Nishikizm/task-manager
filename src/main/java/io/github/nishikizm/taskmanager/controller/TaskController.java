package io.github.nishikizm.taskmanager.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nishikizm.taskmanager.service.TaskService;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    
    @GetMapping("/tasks")
    public String get(Model model) {
        List<TaskResponse> tasks = service.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskForm", new TaskCreateForm("", "", 2026, 1, 1, "00:00", false));
        return "tasks";
    }

    @GetMapping("tasks/create")
    public String getCreateForm(Model model) {
        model.addAttribute("taskForm", new TaskCreateForm("", "", 2026, 1, 1, "00:00", false));
        model.addAttribute("url", "/tasks");
        model.addAttribute("method", "POST");
        model.addAttribute("resetBtnMessage", "Clear");
        model.addAttribute("resetId", "");
        model.addAttribute("resetBtn", "clear");
        model.addAttribute("btnMessage", "Create");
        return "fragments/form :: inputForm";
    }

    // ********　編集中　********
    @GetMapping("tasks/patch/{id}")
    public String getPatchForm(@PathVariable Long id, Model model) {
        TaskCreateForm form = service.findOne(id);
        model.addAttribute("taskForm", form);
        model.addAttribute("url", "/tasks"); // 修正要
        model.addAttribute("method", "PATCH");
        model.addAttribute("resetBtnMessage", "Reset");
        model.addAttribute("resetId", "id");
        model.addAttribute("resetBtn", "reset");
        model.addAttribute("btnMessage", "Update");
        return "fragments/form :: inputForm";
    }

    @GetMapping("/tasks/list")
    public String getList(Model model) {
        List<TaskResponse> tasks = service.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskForm", new TaskCreateForm("", "", 2026, 1, 1, "00:00", false));
        return "fragments/list :: taskList";
    }

    @PostMapping("/tasks")
    @ResponseBody
    public ResponseEntity<Void> create(@Valid @ModelAttribute("taskForm") 
        TaskCreateForm form, 
        BindingResult binding) {
        
        try {
            service.createTask(form);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tasks")
    @ResponseBody
    public int delete(@RequestBody List<Long> idList) {
        return service.deleteTask(idList);
    }

}
