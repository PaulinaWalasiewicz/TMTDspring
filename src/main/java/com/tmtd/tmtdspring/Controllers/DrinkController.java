package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.Category;
import com.tmtd.tmtdspring.Models.Drink;
import com.tmtd.tmtdspring.Repository.DrinkRepository;
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
public class DrinkController {
    @Autowired
    DrinkRepository drinkRepository;

    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrinks(){
        try{
            List<Drink> drinks = new ArrayList<Drink>();
            if(drinks.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drinks,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/drink/{id}")
    public ResponseEntity<Drink> getDrink(@PathVariable("id") long id){
        Optional<Drink> drink = drinkRepository.findById(id);
        if(drink.isPresent()){
            return new ResponseEntity<>(drink.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/drink/create")
    public ResponseEntity<Drink> createDrink(@RequestBody Drink drink){
        try{
            Drink drink1 = drinkRepository
                    .save(new Drink(drink.getUser(), drink.getCount(),drink.getLimit(),drink.getDrinkType(),drink.getUnit()));
            return  new ResponseEntity<>(drink1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/drink/update/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable("id") long id, @RequestBody Drink drink) {
        Optional<Drink> optionalDrink = drinkRepository.findById(id);

        if (optionalDrink.isPresent()) {
            Drink drink1 = optionalDrink.get();
            drink1.setLimit(drink.getLimit());
            drink1.setDrinkType(drink.getDrinkType());
            drink1.setUser(drink.getUser());
            drink1.setCount(drink.getCount());
            return new ResponseEntity<>(drinkRepository.save(drink1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/drink/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDrink(@PathVariable("id") long id) {
        try {
            drinkRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/drinks")
    public ResponseEntity<HttpStatus> deleteAllDrink() {
        try {
            drinkRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
