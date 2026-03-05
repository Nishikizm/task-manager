package io.github.nishikizm.taskmanager.web.request;

import java.time.Instant;
import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskPatchForm(

    @NotBlank(message = "Titleは入力必須です")
    @Size(max = 20, message = "Titleは20文字以内で入力してください")
    Optional<String> title, 

    @Size(max = 200, message = "Descriptionは200文字以内で入力してください")
    Optional<String> description, 

    Optional<Instant> deadline, 

    Optional<Boolean> completed

) {}
