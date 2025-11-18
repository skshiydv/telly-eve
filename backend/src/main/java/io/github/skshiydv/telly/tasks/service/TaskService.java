package io.github.skshiydv.telly.tasks.service;

import io.github.skshiydv.telly.tasks.entity.TaskEntity;
import io.github.skshiydv.telly.tasks.model.CreateTaskRq;

import java.util.List;

public interface TaskService {
    public List<TaskEntity> getTasksByStatus(boolean status);
    public List<TaskEntity> getTasksOfCurrUser();
    public String createTask(CreateTaskRq createTaskRq);
    public String updateTask(Long id,boolean status);
}
