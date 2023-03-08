package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.Category;
import com.tmtd.tmtdspring.Models.Description;
import com.tmtd.tmtdspring.Repository.CategoryRepository;
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
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        try{
            List<Category> categories = new ArrayList<Category>();
            categoryRepository.findAll().forEach(categories::add);
            if(categories.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return new ResponseEntity<>(category.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        try{
            Category category1 = categoryRepository
                    .save(new Category(category.getContent()));
            return  new ResponseEntity<>(category1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category1 = optionalCategory.get();
            category1.setContent(category.getContent());
            return new ResponseEntity<>(categoryRepository.save(category1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/categories")
    public ResponseEntity<HttpStatus> deleteAllCategory() {
        try {
            categoryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
