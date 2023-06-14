package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Models.Event;
import com.tmtd.tmtdspring.Repository.EventRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 *Klasa EventController implementuje różne funkcje związane z zarządzaniem wydarzeniami, takie jak pobieranie wydarzeń dla konkretnego użytkownika, tworzenie, aktualizowanie i usuwanie wydarzeń.
 *
 * Klasa ta ma dwa pola oznaczone adnotacją @Autowired: eventRepository i userRepository. Te pola są wstrzykiwane przez mechanizm Springa i odnoszą się do repozytoriów danych, EventRepository i UserRepository odpowiednio. Wstrzykiwanie zależności pozwala na łatwe korzystanie z funkcjonalności tych repozytoriów w obrębie EventController.
 *
 * Następnie mamy kilka metod oznaczonych adnotacją @GetMapping, @PostMapping, @PutMapping lub @DeleteMapping, które obsługują różne żądania HTTP.
 *
 * Metoda getAllEventsByUserId() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/events, gdzie {user_id} to zmienna ścieżkowa, i zwraca wszystkie wydarzenia przypisane do określonego identyfikatora użytkownika.
 * Metoda getEventsById() obsługuje żądanie GET pod ścieżką /api/events/{id}, gdzie {id} to zmienna ścieżkowa, i zwraca szczegółowe informacje o wydarzeniu o określonym identyfikatorze.
 * Metoda createEvent() obsługuje żądanie POST pod ścieżką /api/users/{user_id}/events i tworzy nowe wydarzenie przypisane do określonego użytkownika na podstawie przekazanych danych. Może również zawierać opcjonalny parametr zapytania description, który reprezentuje opis wydarzenia.
 * Metoda updateEvent() obsługuje żądanie PUT pod ścieżką /api/events/{id}, gdzie {id} to zmienna ścieżkowa, i aktualizuje istniejące wydarzenie o określonym identyfikatorze na podstawie przekazanych danych.
 * Metoda deleteEvent() obsługuje żądanie DELETE pod ścieżką /api/events/{id}, gdzie {id} to zmienna ścieżkowa, i usuwa wydarzenie o określonym identyfikatorze.
 * Metoda deleteAllEventsOfUser() obsługuje żądanie DELETE pod ścieżką /api/users/{user_id}/events, gdzie {user_id} to zmienna ścieżkowa, i usuwa wszystkie wydarzenia przypisane do określonego identyfikatora użytkownika.
 * Każda z tych metod zwraca obiekt ResponseEntity, który reprezentuje odpowiedź HTTP, wraz z odpowiednim kodem statusu i treścią odpowiedzi.
 *
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{user_id}/events")
    //@GetMapping("/users/events")
    public ResponseEntity<List<Event>> getAllEventsByUserId(@PathVariable(value = "user_id") Long user_id) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Event> events = eventRepository.findByUserId(user_id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventsById(@PathVariable(value = "id") Long id) {
        Optional<Event> event = eventRepository.findById(id);

        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users/{user_id}/events")
    //@PostMapping("/users/events")
    public ResponseEntity<Event> createEvent(@PathVariable("user_id") long user_id, @RequestParam(required = false) String description ,@RequestBody Event eventRequest) {

        Optional<Event> event = userRepository.findById(user_id).map(user-> {
            eventRequest.setUser(user);
            eventRequest.setDescription(description);
            return eventRepository.save(eventRequest);
        });


        return event.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody Event eventRequest) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            event.get().setTitle(eventRequest.getTitle());
            event.get().setStartTime(eventRequest.getStartTime());
            event.get().setEndTime(eventRequest.getEndTime());
            //event.get().setDescription(eventRequest.getDescription());
            return new ResponseEntity<>(eventRepository.save(event.get()),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("id") Long id) {
        eventRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{user_id}/events")

    public ResponseEntity<List<Event>> deleteAllEventsOfUser(@PathVariable(value = "user_id") Long user_id) {
        if (userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            eventRepository.deleteByUserId(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
