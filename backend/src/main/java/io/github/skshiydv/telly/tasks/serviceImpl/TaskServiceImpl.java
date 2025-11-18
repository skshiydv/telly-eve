package io.github.skshiydv.telly.tasks.serviceImpl;

import io.github.skshiydv.telly.tasks.entity.TaskEntity;
import io.github.skshiydv.telly.tasks.model.CreateTaskRq;
import io.github.skshiydv.telly.tasks.repository.TaskRepository;
import io.github.skshiydv.telly.tasks.service.TaskService;
import io.github.skshiydv.telly.user.entity.UserEntity;
import io.github.skshiydv.telly.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskEntity> getTasksByStatus(boolean status) {
        List<TaskEntity> tasks = new ArrayList<>();
        return List.of();
    }

    @Override
    public List<TaskEntity> getTasksOfCurrUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            OAuth2User user = (OAuth2User) auth.getPrincipal();
            String email = user.getAttribute("email");
            UserEntity userEntity = userRepository.findByEmail(email);
            return userEntity.getTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createTask(CreateTaskRq createTaskRq) {
        TaskEntity taskEntity = new TaskEntity();
      try {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          OAuth2User user = (OAuth2User) auth.getPrincipal();
          String email = user.getAttribute("email");
          UserEntity userEntity = userRepository.findByEmail(createTaskRq.getAssignedTo());
          taskEntity.setAssignee(email);
          taskEntity.setAssignedTo(createTaskRq.getAssignedTo());
          taskEntity.setTask(createTaskRq.getTask());
          taskEntity.setDueDate(createTaskRq.getDueDate());
          taskEntity.setCompleted(false);
          taskRepository.save(taskEntity);
          userEntity.getTasks().add(taskEntity);
          userRepository.save(userEntity);
      }catch (Exception e){
          throw new RuntimeException(e.getMessage());
      }
        return "Task created";
    }

    @Override
    public String updateTask(Long id , boolean status) {
        TaskEntity task = taskRepository.findById(id).get();
        task.setCompleted(status);
        taskRepository.save(task);
        return "updated task";
    }
}
