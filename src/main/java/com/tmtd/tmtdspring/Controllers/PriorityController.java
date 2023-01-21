package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.Priority;
import com.tmtd.tmtdspring.Models.User;
import com.tmtd.tmtdspring.Repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PriorityController {
    @Autowired
    PriorityRepository priorityRepository;

    @GetMapping("/priority")
    public ResponseEntity<List<Priority>> getAllPriorities(){
        try{
            List<Priority> priorities = new ArrayList<Priority>();
            priorityRepository.findAll().forEach(priorities::add);
            if(priorities.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(priorities,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/priority/{id}")
    public ResponseEntity<Priority> getPriority(@PathVariable("id") long id){
        Optional<Priority> priorityOptional = priorityRepository.findById(id);
        if(priorityOptional.isPresent()){
            return new ResponseEntity<>(priorityOptional.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/priority")
    public ResponseEntity<Priority> createPriority(@RequestBody Priority priority){
        try{
            Priority priority1 = priorityRepository
                    .save(new Priority(priority.getContent()));
            return  new ResponseEntity<>(priority1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/priority/update/{id}")
    public ResponseEntity<Priority> updatePriority(@PathVariable("id") long id, @RequestBody Priority priority) {
        Optional<Priority> priority1 = priorityRepository.findById(id);

        if (priority1.isPresent()) {
            Priority priority2 = priority1.get();
            priority2.setContent(priority.getContent());
            return new ResponseEntity<>(priorityRepository.save(priority2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/priority/delete/{id}")
    public ResponseEntity<HttpStatus> deletePriority(@PathVariable("id") long id) {
        try {
            priorityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/priorities")
    public ResponseEntity<HttpStatus> deleteAllPriorities() {
        try {
            priorityRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
