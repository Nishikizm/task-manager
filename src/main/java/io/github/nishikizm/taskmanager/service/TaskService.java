package io.github.nishikizm.taskmanager.service;

import java.time.Instant;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.nishikizm.taskmanager.domain.entity.Task;
import io.github.nishikizm.taskmanager.domain.exception.TaskNotFoundException;
import io.github.nishikizm.taskmanager.mapper.TaskMapper;
import io.github.nishikizm.taskmanager.mapper.converter.DeadlineConverter;
import io.github.nishikizm.taskmanager.repository.TaskRepository;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.request.TaskPatchForm;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Service
public class TaskService {
    
    private final DeadlineConverter converter;
    private final TaskMapper mapper;
    private final TaskRepository repository;

    public TaskService(DeadlineConverter converter, TaskMapper mapper, TaskRepository repository) {
        this.converter = converter;
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public void createTask(@Valid TaskCreateForm form) {
        Instant deadline = converter.toInstant(form.year(), form.month(), form.day(), form.time());
        Task task = mapper.toEntity(form.title(), form.description(), deadline);
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

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        return repository.findAll().stream()
            .map(mapper::toDTO)
            .toList();
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        repository.delete(task);
    }
}
