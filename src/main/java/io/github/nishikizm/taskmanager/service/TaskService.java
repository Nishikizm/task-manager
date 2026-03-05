package io.github.nishikizm.taskmanager.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nishikizm.taskmanager.domain.entity.Task;
import io.github.nishikizm.taskmanager.domain.exception.TaskNotFoundException;
import io.github.nishikizm.taskmanager.mapper.TaskMapper;
import io.github.nishikizm.taskmanager.repository.TaskRepository;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.request.TaskPatchForm;

@Service
public class TaskService {
    
    private final TaskMapper mapper;
    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskMapper mapper, TaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public void createTask(@Valid TaskCreateForm form) {
        Task task = mapper.toEntity(form);
        repository.save(task);
    }

    @Transactional
    public void patchTask(Long id, @Valid TaskPatchForm form) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        
        form.title().ifPresent(task::changeTitle);
        form.description().ifPresent(task::changeDescription);
        form.deadline().ifPresent(task::changeDeadline);
        form.completed().ifPresent(c -> { if(c) task.complete(); else task.reopen(); });

    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        repository.delete(task);
    }
}
