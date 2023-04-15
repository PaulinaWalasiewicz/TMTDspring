package com.tmtd.tmtdspring.Controllers;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.tmtd.tmtdspring.Models.*;
import com.tmtd.tmtdspring.Repository.DrinkRepository;
import com.tmtd.tmtdspring.Repository.DrinkTypeRepository;
import com.tmtd.tmtdspring.Repository.LiquidUnitRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DrinkController {
    @Autowired
    DrinkRepository drinkRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DrinkTypeRepository drinkTypeRepository;
    @Autowired
    LiquidUnitRepository liquidUnitRepository;

    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrinks(){
        try{
            List<Drink> drinks = new ArrayList<Drink>();
            drinkRepository.findAll().forEach(drinks::add);

            if(drinks.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drinks,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/users/{user_id}/drinks")
    //@GetMapping("/users/events")
    public ResponseEntity<List<Drink>> getAllEventsByUserId(@PathVariable(value = "user_id") Long user_id) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Drink> drinks = drinkRepository.findByUserId(user_id);
        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }
    //Get user drinks for time frame
    @GetMapping("/users/{user_id}/drinks/{date1}/{date2}")
    public ResponseEntity<List<Drink>> getUsersDrinksFromTime(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "date1") LocalDate date1, @PathVariable(value = "date2") LocalDate date2) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Drink> drinks = drinkRepository.findByUserId(user_id);
        List<Drink> filtered = drinks.stream()
                .filter(d-> d.getDrink_date() != null && d.getDrink_date().isAfter(date1) && d.getDrink_date().isBefore(date2) )
                .toList();

        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/drink")
    public ResponseEntity<List<Drink>> getAllDrinksByUserId(@PathVariable(value = "user_id") long user_id, @RequestParam(required = true) long drink_type_id, @RequestParam(required = true) long drink_unit_id) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Drink> drinks = drinkRepository.findByUserId(user_id);
        DrinkType drinkType = drinkTypeRepository.findById(drink_type_id).get();
        LiquidUnit unit = liquidUnitRepository.findById(drink_unit_id).get();

        List<Drink> drinks2 = drinks.stream().filter(drink -> drink.getDrinkType() == drinkType).toList();
        List<Drink> drinks3 = drinks2.stream().filter(drink -> drink.getUnit() == unit).toList();

        return new ResponseEntity<>(drinks3, HttpStatus.OK);
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

    @PostMapping("/users/{user_id}/drinks")
    public ResponseEntity<Drink> createDrink(@PathVariable("user_id") long user_id, @RequestParam(required = true) long drink_type_id, @RequestParam(required = true) long drink_unit_id, @RequestBody Drink drinkRequest){

        Optional<Drink> drink = userRepository.findById(user_id).map(user-> {
            drinkRequest.setUser(user);
            drinkRequest.setDrinkType(drinkTypeRepository.findById(drink_type_id).get());
            drinkRequest.setUnit(liquidUnitRepository.findById(drink_unit_id).get());
            return drinkRepository.save(drinkRequest);
        });


        return drink.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/drink/update/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable("id") long id, @RequestBody Drink drink) {
        Optional<Drink> optionalDrink = drinkRepository.findById(id);

        if (optionalDrink.isPresent()) {
            Drink drink1 = optionalDrink.get();
            drink1.setLimit(drink.getLimit());
            drink1.setDrinkType(drink.getDrinkType());
            drink1.setUnit(drink.getUnit());
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
