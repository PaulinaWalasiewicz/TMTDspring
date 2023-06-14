package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Models.Category;
import com.tmtd.tmtdspring.Models.Task;
import com.tmtd.tmtdspring.Repository.TaskRepository;
import com.tmtd.tmtdspring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 *Klasa TaskController odpowiada za zarządzanie zadaniami. Ma pole taskRepository i userRepository, które są oznaczone adnotacją @Autowired i są wstrzykiwane przez mechanizm Springa. Pola te odnoszą się odpowiednio do repozytorium danych TaskRepository i UserRepository, które umożliwiają dostęp do operacji na encjach typu Task i User.
 *
 * Następnie mamy kilka metod oznaczonych adnotacją @GetMapping, @PostMapping, @PutMapping lub @DeleteMapping, które obsługują różne żądania HTTP.
 *
 * Metoda getTasksById() obsługuje żądanie GET pod ścieżką /api/tasks/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator zadania. Metoda zwraca szczegółowe informacje o zadaniu o określonym identyfikatorze. Jeśli zadanie nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda getAllTasksByUserId() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/tasks, gdzie {user_id} to zmienna ścieżkowa reprezentująca identyfikator użytkownika. Metoda zwraca listę wszystkich zadań przypisanych do danego użytkownika. Jeśli użytkownik nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda getTasksFromTimeFrame() obsługuje żądanie GET pod ścieżką /api/users/{user_id}/tasks/{date1}/{date2}, gdzie {user_id}, {date1} i {date2} to zmienne ścieżkowe reprezentujące odpowiednio identyfikator użytkownika oraz daty date1 i date2. Metoda zwraca listę zadań przypisanych do danego użytkownika, które mieszczą się w określonym przedziale czasowym. Jeśli użytkownik nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda createTask() obsługuje żądanie POST pod ścieżką /api/users/{user_id}/tasks i tworzy nowe zadanie na podstawie przekazanych danych. Zadanie jest przypisywane do danego użytkownika na podstawie identyfikatora user_id. Opcjonalnie można przekazać dodatkowe parametry: description (opis zadania) i category (kategoria zadania). Zadanie jest zapisywane w repozytorium i zwracane wraz z kodem statusu CREATED. Jeśli wystąpi błąd, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda updateTask() obsługuje żądanie PUT pod ścieżką /api/tasks/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator zadania. Metoda aktualizuje istniejące zadanie na podstawie przekazanych danych i zwraca zaktualizowane zadanie wraz z kodem statusu OK. Jeśli zadanie nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda deleteTask() obsługuje żądanie DELETE pod ścieżką /api/tasks/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator zadania. Metoda usuwa zadanie o podanym identyfikatorze z repozytorium i zwraca kod statusu NO_CONTENT.
 * Metoda deleteAllTasks() obsługuje żądanie DELETE pod ścieżką /api/users/tasks i usuwa wszystkie zadania przypisane do użytkownika o podanym identyfikatorze user_id. Jeśli użytkownik nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda deleteAllTasksOfUser() obsługuje żądanie DELETE pod ścieżką /api/users/{user_id}/tasks i usuwa wszystkie zadania przypisane do użytkownika o podanym identyfikatorze user_id. Jeśli użytkownik nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Odpowiednie odpowiedzi HTTP są zwracane w zależności od wyniku operacji.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTasksById(@PathVariable(value = "id") Long id) {
        Optional<Task> task = taskRepository.findById(id);

        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/users/{user_id}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByUserId(@PathVariable(value = "user_id") Long user_id) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Task> tasks = taskRepository.findByUserId(user_id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    //Returnes tasks from time frame
    @GetMapping("/users/{user_id}/tasks/{date1}/{date2}")
    public ResponseEntity<List<Task>> getTasksFromTimeFrame(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "date1") LocalDate date1, @PathVariable(value = "date2") LocalDate date2) {

        if (!userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Task> tasks = taskRepository.findByUserId(user_id);
        List<Task> filtered = tasks.stream()
                .filter(t-> t.getDueDate() != null && t.getDueDate().toLocalDate().isBefore(date2) && t.getDueDate().toLocalDate().isAfter(date1))
                .toList();



        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
    @PostMapping("/users/{user_id}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable("user_id") long user_id, @RequestParam(required = false) String description, @RequestParam(required = false) String category, @RequestBody Task taskRequest) {

        Optional<Task> task = userRepository.findById(user_id).map(user-> {
            taskRequest.setUser(user);
            taskRequest.setDescription(description);
            taskRequest.setCategory(Category.valueOf(category));
            return taskRepository.save(taskRequest);
        });


        return task.map(value -> new ResponseEntity<>(value,HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task taskRequest) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            task.get().setTitle(taskRequest.getTitle());
            task.get().setDueDate(taskRequest.getDueDate());
            task.get().setCompleted(taskRequest.isCompleted());
            task.get().setPriority(taskRequest.getPriority());
            return new ResponseEntity<>(taskRepository.save(task.get()),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/tasks")
    public ResponseEntity<List<Task>> deleteAllTasks(@PathVariable(value = "user_id") Long user_id) {
        if (userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            taskRepository.deleteByUserId(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/users/{user_id}/tasks")
    public ResponseEntity<List<Task>> deleteAllTasksOfUser(@PathVariable(value = "user_id") Long user_id) {
        if (userRepository.existsById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            taskRepository.deleteByUserId(user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}

