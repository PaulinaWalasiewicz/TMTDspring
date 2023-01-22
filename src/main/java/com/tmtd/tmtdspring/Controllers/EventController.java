package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Models.Event;
import com.tmtd.tmtdspring.Repository.DescriptionRepository;
import com.tmtd.tmtdspring.Repository.EventRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

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
    public ResponseEntity<Event> createEvent(@PathVariable("user_id") long user_id, @RequestParam(required = false) long description_id ,@RequestBody Event eventRequest) {

        Optional<Event> event = userRepository.findById(user_id).map(user-> {
            eventRequest.setUser(user);
            eventRequest.setDescription(descriptionRepository.findById(description_id).get());
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
