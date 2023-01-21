package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.Description;
import com.tmtd.tmtdspring.Models.DrinkType;
import com.tmtd.tmtdspring.Repository.DescriptionRepository;
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
public class DescriptionController {
    @Autowired
    DescriptionRepository descriptionRepository;

    @GetMapping("/descriptions")
    public ResponseEntity<List<Description>> getAllDescription(){
        try{
            List<Description> descriptions = new ArrayList<Description>();
            if(descriptions.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(descriptions,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/description/{id}")
    public ResponseEntity<Description> getDescription(@PathVariable("id") long id){
        Optional<Description> optionalDescription = descriptionRepository.findById(id);
        if(optionalDescription.isPresent()){
            return new ResponseEntity<>(optionalDescription.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/description/create")
    public ResponseEntity<Description> createDescription(@RequestBody Description description){
        try{
            Description description1 = descriptionRepository
                    .save(new Description(description.getcontent()));
            return  new ResponseEntity<>(description1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/description/update/{id}")
    public ResponseEntity<Description> updateDescription(@PathVariable("id") long id, @RequestBody Description description) {
        Optional<Description> optionalDescription = descriptionRepository.findById(id);

        if (optionalDescription.isPresent()) {
            Description description1 = optionalDescription.get();
            description1.setcontent(description.getcontent());
            return new ResponseEntity<>(descriptionRepository.save(description1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/description/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDescription(@PathVariable("id") long id) {
        try {
            descriptionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/descriptions")
    public ResponseEntity<HttpStatus> deleteAllDescriptions() {
        try {
            descriptionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
