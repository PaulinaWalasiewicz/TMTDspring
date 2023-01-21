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
