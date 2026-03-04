package io.github.nishikizm.taskmanager.domain.entity;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Instant deadline;

    @Column(nullable = false)
    private boolean completed;

    public Task(String title, String description, Instant deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        completed = false;
    }

    public void changeTitle(String title) { this.title = title; }
    public void changeDescription(String description) { this.description = description; }
    public void changeDeadline(Instant deadline) { this.deadline = deadline; }

    public void complete() {
        if(completed) { return; }
        completed = true;
    }

    public void reopen() {
        if(!completed) { return; }
        completed = false;
    }
}
