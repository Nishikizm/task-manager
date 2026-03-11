package io.github.nishikizm.taskmanager.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;
import io.github.nishikizm.taskmanager.domain.entity.Task;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Component
public class TaskMapper {
    
    public Task toEntity(String title, String description, Instant deadline) {
        return new Task(
            title, 
            description, 
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

}
