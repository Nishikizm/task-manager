package io.github.nishikizm.taskmanager.web.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskCreateForm(

    @NotBlank(message = "Titleは入力必須です")
    @Size(max = 20, message = "Titleは20文字以内で入力してください")
    String title, 

    @Size(max = 200, message = "Descriptionは200文字以内で入力してください")
    String description, 

    @NotNull(message = "Deadlineは入力必須です")
    @Min(value = 2026, message = "年の形式が不正です")
    @Max(value = 2036, message = "年の形式が不正です")
    Integer year,

    @NotNull(message = "Deadlineは入力必須です")
    @Min(value = 1, message = "月の形式が不正です")
    @Max(value = 12, message = "月の形式が不正です")
    Integer month,

    @NotNull(message = "Deadlineは入力必須です")
    @Min(value = 1, message = "日の形式が不正です")
    @Max(value = 31, message = "日の形式が不正です")
    Integer day, 

    @NotBlank(message = "Deadlineは入力必須です")
    @Pattern(regexp = "^[0-2]\\d:[00|30]$")
    String time, 

    boolean completed

) {}
