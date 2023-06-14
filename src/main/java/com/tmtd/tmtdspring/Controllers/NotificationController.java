package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.Notification;
import com.tmtd.tmtdspring.Repository.NotificationRepository;
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

/**
 *Klasa NotificationController ma pole notificationRepository, które jest oznaczone adnotacją @Autowired i jest wstrzykiwane przez mechanizm Springa. To pole odnosi się do repozytorium danych NotificationRepository, które umożliwia dostęp do operacji na encjach typu Notification.
 *
 * Następnie mamy kilka metod oznaczonych adnotacją @GetMapping, @PostMapping, @PutMapping lub @DeleteMapping, które obsługują różne żądania HTTP.
 *
 * Metoda getAllNotifications() obsługuje żądanie GET pod ścieżką /api/notification i zwraca listę wszystkich powiadomień. Jeśli lista jest pusta, zostanie zwrócony kod statusu NO_CONTENT. W przeciwnym razie zwrócone zostaną powiadomienia wraz z kodem statusu OK.
 * Metoda getPNotification() obsługuje żądanie GET pod ścieżką /api/notification/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator powiadomienia. Metoda zwraca szczegółowe informacje o powiadomieniu o określonym identyfikatorze. Jeśli powiadomienie nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda createNotification() obsługuje żądanie POST pod ścieżką /api/notification/create i tworzy nowe powiadomienie na podstawie przekazanych danych. Powiadomienie jest zapisywane w repozytorium i zwracane wraz z kodem statusu CREATED. Jeśli wystąpi błąd, zostanie zwrócony kod statusu INTERNAL_SERVER_ERROR.
 * Metoda updateNotification() obsługuje żądanie PUT pod ścieżką /api/notification/update/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator powiadomienia do zaktualizowania. Metoda aktualizuje istniejące powiadomienie o określonym identyfikatorze na podstawie przekazanych danych i zwraca zaktualizowane powiadomienie wraz z kodem statusu OK. Jeśli powiadomienie nie istnieje, zostanie zwrócony kod statusu NOT_FOUND.
 * Metoda deleteNotification() obsługuje żądanie DELETE pod ścieżką /api/notification/delete/{id}, gdzie {id} to zmienna ścieżkowa reprezentująca identyfikator powiadomienia do usunięcia. Metoda usuwa powiadomienie o określonym identyfikatorze z repozytorium i zwraca kod statusu NO_CONTENT. Jeśli wystąpi błąd, zostanie zwrócony kod statusu INTERNAL_SERVER_ERROR.
 * Metoda deleteAllNotifications() obsługuje żądanie DELETE pod ścieżką /api/notifications. Metoda usuwa wszystkie powiadomienia z repozytorium i zwraca kod statusu NO_CONTENT. Jeśli wystąpi błąd, zostanie zwrócony kod statusu INTERNAL_SERVER_ERROR.
 * Te metody umożliwiają manipulację danymi powiadomień poprzez wykonywanie różnych operacji, takich jak pobieranie wszystkich powiadomień, tworzenie nowych powiadomień, aktualizowanie istniejących powiadomień i usuwanie powiadomień. Odpowiednie odpowiedzi HTTP są zwracane w zależności od wyniku operacji.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/notification")
    public ResponseEntity<List<Notification>> getAllNotifications(){
        try{
            List<Notification> notifications = new ArrayList<Notification>();
            if(notifications.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(notifications,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> getPNotification(@PathVariable("id") long id){
        Optional<Notification> notificationOptionalOptional = notificationRepository.findById(id);
        if(notificationOptionalOptional.isPresent()){
            return new ResponseEntity<>(notificationOptionalOptional.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/notification/create")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        try{
            Notification notification1 = notificationRepository
                    .save(new Notification(notification.getEvent(),notification.getTitle(),notification.getMessage(),notification.getTimestamp()));
            return  new ResponseEntity<>(notification1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/notification/update/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") long id, @RequestBody Notification notification) {
        Optional<Notification> notification1 = notificationRepository.findById(id);

        if (notification1.isPresent()) {
            Notification notification2 = notification1.get();
            notification2.setMessage(notification.getMessage());
            notification2.setTimestamp(notification.getTimestamp());
            notification2.setTitle(notification.getTitle());
            notification2.setEvent(notification.getEvent());
            return new ResponseEntity<>(notificationRepository.save(notification2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/notification/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable("id") long id) {
        try {
            notificationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<HttpStatus> deleteAllNotifications() {
        try {
            notificationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
