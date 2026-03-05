package io.github.nishikizm.taskmanager.web.request;

import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskCreateForm(

    @NotBlank(message = "Titleは入力必須です")
    @Size(max = 20, message = "Titleは20文字以内で入力してください")
    String title, 

    @Size(max = 200, message = "Descriptionは200文字以内で入力してください")
    String description, 

    @NotNull(message = "Deadlineは入力必須です")
    Instant deadline

) {}
