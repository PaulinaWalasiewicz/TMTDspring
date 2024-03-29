package com.tmtd.tmtdspring.Controllers;
import com.tmtd.tmtdspring.Models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *Klasa UserController ma pole userRepository, które jest oznaczone adnotacją @Autowired i jest wstrzykiwane przez mechanizm Springa. Pole to odnosi się do repozytorium danych UserRepository, które umożliwia dostęp do operacji na encjach typu User.
 *
 * Następnie mamy kilka metod oznaczonych adnotacją @GetMapping, @PostMapping, @PutMapping lub @DeleteMapping, które obsługują różne żądania HTTP.
 *
 * Metoda getAllUsers() obsługuje żądanie GET pod ścieżką /api/users. Może również przyjmować opcjonalny parametr zapytania email. Metoda zwraca listę wszystkich użytkowników lub użytkowników o określonym adresie email, jeśli parametr email jest przekazany. Jeśli lista użytkowników jest pusta lub nie przekazano parametru email, zostanie zwrócony kod statusu NO_CONTENT. W przypadku wystąpienia błędu zostanie zwrócony kod statusu INTERNAL_SERVER_ERROR.
 * Metoda createUser() obsługuje żądanie GET pod ścieżką /api/user/create/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator użytkownika. Metoda zwraca użytkownika o określonym identyfikatorze, jeśli istnieje. W przeciwnym razie zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda createUser() obsługuje żądanie POST pod ścieżką /api/user/create i tworzy nowego użytkownika na podstawie przekazanych danych w formacie JSON. Użytkownik jest zapisywany w repozytorium i zwracany wraz z kodem statusu CREATED. Jeśli wystąpi błąd, zostanie zwrócony kod statusu INTERNAL_SERVER_ERROR.
 * Metoda updateUser() obsługuje żądanie PUT pod ścieżką /api/user/update/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator użytkownika. Metoda aktualizuje istniejącego użytkownika na podstawie przekazanych danych i zwraca zaktualizowanego użytkownika wraz z kodem statusu OK. Jeśli użytkownik nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda deleteUser() obsługuje żądanie DELETE pod ścieżką /api/user/delete/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator użytkownika. Metoda usuwa użytkownika o podanym identyfikatorze z repozytorium i zwraca kod statusu NO_CONTENT.
 * Metoda deleteAllUsers() obsługuje żądanie DELETE pod ścieżką /api/users/delete. Metoda usuwa wszystkich użytkowników z repozytorium i zwraca kod statusu NO_CONTENT.
 * Odpowiednie odpowiedzi HTTP są zwracane w zależności od wyniku operacji.
 *
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String email){
        try{
            List<User> users = new ArrayList<User>();

            if(email == null){
                userRepository.findAll().forEach(users::add);
            }
            if(users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/create/{id}")
    public ResponseEntity<User> createUser(@PathVariable("id") long id){
        Optional<User> usersData = userRepository.findById(id);
        if(usersData.isPresent()){
            return new ResponseEntity<>(usersData.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User _user = userRepository
                    .save(new User(user.getUsername(),user.getPassword(),user.getEmail(), user.getFirstName(), user.getLastName()));
            return  new ResponseEntity<>(_user,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setUsername(user.getUsername());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//new
}
