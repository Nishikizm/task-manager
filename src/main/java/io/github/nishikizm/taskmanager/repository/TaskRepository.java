package io.github.nishikizm.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nishikizm.taskmanager.domain.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {}
