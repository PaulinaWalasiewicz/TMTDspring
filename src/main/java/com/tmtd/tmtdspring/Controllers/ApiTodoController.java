package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApiTodoController {
    private List<Task> tasks;

    public ApiTodoController() {
        tasks = new ArrayList<>();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/todos")
    public ResponseEntity<Task> createTask(@RequestBody Task newTask){
        newTask.setId(UUID.randomUUID().getMostSignificantBits());
        tasks.add(newTask);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/todos")
    public ResponseEntity<Task> updateTask(@RequestBody Task updatedTask){
        Optional<Task> existingTask = tasks
                .stream()
                .filter(task -> task.getId().equals(updatedTask.getId()))
                .findAny();
        if(existingTask.isPresent()){
            tasks.remove(existingTask.get());
            tasks.add(updatedTask);
        }
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/todos")
    public ResponseEntity<Task> deleteTask(@RequestParam("id") Long id){
        Optional<Task> foundTask = tasks
                .stream()
                .filter(task -> task.getId().equals(id))
                .findAny();
        if (foundTask.isPresent()) {
            boolean remove =tasks.remove(foundTask.get());
            if(remove) {
                return ResponseEntity.ok(foundTask.get());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
