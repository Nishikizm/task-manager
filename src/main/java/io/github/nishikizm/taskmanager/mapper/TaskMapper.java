package io.github.nishikizm.taskmanager.mapper;

import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.github.nishikizm.taskmanager.domain.entity.Task;
import io.github.nishikizm.taskmanager.mapper.converter.DeadlineConverter;
import io.github.nishikizm.taskmanager.web.form.DeadlineParts;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final DeadlineConverter converter;
    
    public Task toEntity(TaskCreateForm form) {
        Instant deadline = converter.toInstant(
            new DeadlineParts(form.year(), form.month(), form.day(), form.time())
        );
        return new Task(
            form.title(), 
            form.description(), 
            deadline
        );
    }

    public TaskResponse toDTO(Task task) {
        return new TaskResponse(
            task.getId(), 
            task.getTitle(), 
            task.getDescription(), 
            task.getDeadline(), 
            task.isCompleted()
        );
    }

    public TaskCreateForm toCreateForm(Task task) {
        DeadlineParts parts = converter.toParts(task.getDeadline());
        return new TaskCreateForm(
            task.getTitle(), 
            task.getDescription(),
            parts.year(),
            parts.month(), 
            parts.day(),
            parts.time(),
            task.isCompleted()
        );
    }

}
