package io.github.nishikizm.taskmanager.web.response;

import java.time.Instant;

public record TaskResponse(
    Long id,
    String title, 
    String description, 
    Instant deadline, 
    boolean completed
) {}
