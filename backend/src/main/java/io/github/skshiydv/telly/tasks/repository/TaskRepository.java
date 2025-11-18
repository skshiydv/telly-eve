package io.github.skshiydv.telly.tasks.repository;

import io.github.skshiydv.telly.tasks.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    void findAllByCompleted(boolean status);
}
