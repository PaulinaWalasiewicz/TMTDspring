package com.tmtd.tmtdspring.Controllers;
import java.time.LocalDate;
import java.util.*;

import com.tmtd.tmtdspring.Models.*;
import com.tmtd.tmtdspring.Repository.DrinkRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *Klasa DrinkController implementuje różne funkcje pozwalające na zarządzanie napojami, takie jak pobieranie wszystkich napojów, pobieranie napojów dla konkretnego użytkownika, tworzenie i aktualizowanie napojów, a także usuwanie pojedynczych napojów lub wszystkich napojów.
 *
 * Klasa ta ma dwa pola oznaczone adnotacją @Autowired: drinkRepository i userRepository. Te pola są wstrzykiwane przez mechanizm Springa i odnoszą się do repozytoriów danych, DrinkRepository i UserRepository odpowiednio. Wstrzykiwanie zależności pozwala na łatwe korzystanie z funkcjonalności tych repozytoriów w obrębie DrinkController.
 *
 * Następnie mamy wiele metod oznaczonych adnotacją @GetMapping, @PostMapping, @PutMapping lub @DeleteMapping, które obsługują różne żądania HTTP.
 *
 * Metoda getAllDrinks() obsługuje żądanie GET pod ścieżką /api/drinks i zwraca wszystkie napoje znajdujące się w repozytorium drinkRepository.
 * Metoda getAllEventsByUserId() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/drinks, gdzie {user_id} to zmienna ścieżkowa, i zwraca wszystkie napoje przypisane do określonego identyfikatora użytkownika.
 * Metoda getUsersDrinksFromTime() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/drinks/{date1}/{date2}, gdzie {user_id}, {date1} i {date2} to zmienne ścieżkowe, i zwraca napoje użytkownika dla określonego zakresu czasowego.
 * Metoda getAllDrinksByUserId() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/drink i akceptuje dwa parametry zapytania (drink_type i drink_unit). Zwraca napoje użytkownika o określonym identyfikatorze, które pasują do określonego typu napoju i jednostki.
 * Metoda getDrink() obsługuje żądanie GET pod ścieżką /api/drink/{id}, gdzie {id} to zmienna ścieżkowa, i zwraca szczegółowe informacje o napoju o określonym identyfikatorze.
 * Metoda createDrink() obsługuje żądanie POST pod ścieżką /api/users/{user_id}/drinks i tworzy nowy napój przypisany do określonego użytkownika na podstawie przekazanych danych.
 * Metoda updateDrink() obsługuje żądanie PUT pod ścieżką /api/drink/update/{id}, gdzie {id} to zmienna ścieżkowa, i aktualizuje istniejący napój o określonym identyfikatorze na podstawie przekazanych danych.
 * Metoda deleteDrink() obsługuje żądanie DELETE pod ścieżką /api/drink/delete/{id}, gdzie {id} to zmienna ścieżkowa, i usuwa napój o określonym identyfikatorze.
 * Metoda deleteAllDrink() obsługuje żądanie DELETE pod ścieżką /api/drinks i usuwa wszystkie napoje z repozytorium drinkRepository.
 *
 * Każda z tych metod zwraca obiekt ResponseEntity, który reprezentuje odpowiedź HTTP, wraz z odpowiednim kodem statusu i treścią odpowiedzi.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DrinkController {
    @Autowired
    DrinkRepository drinkRepository;
    @Autowired
    UserRepository userRepository;


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
    public ResponseEntity<List<Drink>> getAllDrinksByUserId(@PathVariable(value = "user_id") long user_id, @RequestParam(required = true) String drink_type, @RequestParam(required = true) String drink_unit) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Drink> drinks = drinkRepository.findByUserId(user_id);

        List<Drink> drinks2 = drinks.stream().filter(drink -> drink.getDrink_type().toString() == drink_type).toList();
        List<Drink> drinks3 = drinks2.stream().filter(drink -> drink.getUnit().toString() == drink_unit).toList();

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
    public ResponseEntity<Drink> createDrink(@PathVariable("user_id") long user_id, @RequestParam(required = true) String drink_type, @RequestParam(required = true) String unit, @RequestBody Drink drinkRequest){

        Optional<Drink> drink = userRepository.findById(user_id).map(user-> {
            drinkRequest.setUser(user);
            drinkRequest.setDrink_type(DrinkType.valueOf(drink_type));
            drinkRequest.setUnit(LiquidUnit.valueOf(unit));
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
            drink1.setDrink_type(drink.getDrink_type());
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
