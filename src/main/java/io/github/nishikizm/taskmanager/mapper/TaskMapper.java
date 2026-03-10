package io.github.nishikizm.taskmanager.mapper;

import org.springframework.stereotype.Component;
import io.github.nishikizm.taskmanager.domain.entity.Task;
import io.github.nishikizm.taskmanager.web.request.TaskCreateForm;
import io.github.nishikizm.taskmanager.web.response.TaskResponse;

@Component
public class TaskMapper {
    
    public Task toEntity(TaskCreateForm form) {
        return new Task(
            form.title(), 
            form.description(), 
            form.deadline()
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
