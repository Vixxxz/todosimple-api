package com.todo.todosimple.services;

import com.todo.todosimple.models.Task;
import com.todo.todosimple.models.User;
import com.todo.todosimple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException  (
                                                                "Tarefa não encontrada." + id + ", Tipo: " + Task.class.getName()
                                                            ));
    }

    public List<Task> findAllByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    @Transactional
    public Task create(Task task) {
        User user = userService.findById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);
        task = taskRepository.save(task);
        return task;
    }

    @Transactional
    public Task updateTask(Task task) {
        userService.findById(task.getUser().getId());
        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());
        return taskRepository.save(newTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findById(id);
        try {
            taskRepository.delete(task);
        }
        catch (Exception e) {
            throw new RuntimeException("Não foi possível excluir a tarefa. Tarefa relacionada a outro recurso.");
        }
    }
}
