package com.todo.todosimple.controllers;

import com.todo.todosimple.models.Task;
import com.todo.todosimple.services.TaskService;
import com.todo.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById (@ PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/user/{userId}")   //nome do parametro recebido tem que estar igual do que esta no @GetMapping
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        userService.findById(userId);
        List<Task> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> createTask (@Valid @RequestBody Task task) {
        task.setId(null);
        taskService.create(task);
        URI uri =   ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask (@Valid @RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask (@PathVariable Long id) {
        Task task = taskService.findById(id);
        taskService.deleteTask(task.getId());
        return ResponseEntity.noContent().build();
    }
}
