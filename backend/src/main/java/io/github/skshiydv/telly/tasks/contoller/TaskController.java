package io.github.skshiydv.telly.tasks.contoller;

import io.github.skshiydv.telly.tasks.entity.TaskEntity;
import io.github.skshiydv.telly.tasks.model.CreateTaskRq;
import io.github.skshiydv.telly.tasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateTaskRq createTaskRq) {
        return new ResponseEntity<>(taskService.createTask(createTaskRq), HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<List<TaskEntity>> getTasksByEmail() {
        return new ResponseEntity<>(taskService.getTasksOfCurrUser(), HttpStatus.OK);
    }
    @GetMapping("/get/{status}")
    public ResponseEntity<List<TaskEntity>> getTasksByStatus(@PathVariable String status) {
        return null;
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id ,@RequestBody boolean status) {
        return new ResponseEntity<>( taskService.updateTask(id,status), HttpStatus.OK);
    }

}
