package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Repository.TaskRepository;
import com.tmtd.tmtdspring.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @PostMapping
    public Task save(@RequestBody Task task){
        return taskRepository.save(task);
    }
}
