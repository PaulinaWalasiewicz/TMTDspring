package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Models.Task;
import com.tmtd.tmtdspring.Repository.TaskRepository;
import com.tmtd.tmtdspring.Repository.CategoryRepository;
import com.tmtd.tmtdspring.Repository.DescriptionRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTasksById(@PathVariable(value = "id") Long id) {
        Optional<Task> task = taskRepository.findById(id);

        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/users/{user_id}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByUserId(@PathVariable(value = "user_id") Long user_id) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Task> tasks = taskRepository.findByUserId(user_id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    //Returnes tasks for from time frame
    @GetMapping("/users/{user_id}/tasks/{date1}/{date2}")
    public ResponseEntity<List<Task>> getTasksFromTimeFrame(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "date1") LocalDate date1, @PathVariable(value = "date2") LocalDate date2) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Task> tasks = taskRepository.findByUserId(user_id);
        List<Task> filtered = tasks.stream()
                .filter(t-> t.getDueDate() != null && t.getDueDate().toLocalDate().isBefore(date2) && t.getDueDate().toLocalDate().isAfter(date1))
                .toList();



        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
    @PostMapping("/users/{user_id}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable("user_id") long user_id, @RequestParam(required = false) long description_id, @RequestParam(required = false) long category_id, @RequestBody Task taskRequest) {

        Optional<Task> task = userRepository.findById(user_id).map(user-> {
            taskRequest.setUser(user);
            taskRequest.setDescription(descriptionRepository.findById(description_id).get());
            taskRequest.setCategory(categoryRepository.findById(category_id).get());
            return taskRepository.save(taskRequest);
        });


        return task.map(value -> new ResponseEntity<>(value,HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task taskRequest) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            task.get().setTitle(taskRequest.getTitle());
            task.get().setDueDate(taskRequest.getDueDate());
            task.get().setCompleted(taskRequest.isCompleted());
            task.get().setPriority(taskRequest.getPriority());
            return new ResponseEntity<>(taskRepository.save(task.get()),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/tasks")
    public ResponseEntity<List<Task>> deleteAllTasks(@PathVariable(value = "user_id") Long user_id) {
        if (userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            taskRepository.deleteByUserId(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/users/{user_id}/tasks")
    public ResponseEntity<List<Task>> deleteAllTasksOfUser(@PathVariable(value = "user_id") Long user_id) {
        if (userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            taskRepository.deleteByUserId(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}

