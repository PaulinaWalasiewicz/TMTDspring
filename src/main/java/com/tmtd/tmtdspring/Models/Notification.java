package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 *Klasa Notification jest adnotowana jako @Entity, co oznacza, że reprezentuje encję w bazie danych. Jest mapowana do tabeli o nazwie "NOTIFICATION" za pomocą adnotacji @Table(name="NOTIFICATION").
 *
 * Posiada następujące pola:
 *
 * id: Pole identyfikatora encji, które jest oznaczone adnotacją @Id. Jest generowane automatycznie za pomocą strategii GenerationType.TABLE.
 * event: Pole reprezentujące powiązane zdarzenie (Event), z którym powiązana jest notyfikacja. Jest to pole klucza obcego oznaczone adnotacją @ManyToOne. W bazie danych jest mapowane jako pole event_id, które nie może być puste (nullable = false). Powiązanie jest typu leniwego (fetch=FetchType.LAZY), co oznacza, że dane związane z zdarzeniem będą pobierane tylko wtedy, gdy będą potrzebne.
 * title: Pole przechowujące tytuł notyfikacji, oznaczone adnotacją @Column(name="title").
 * message: Pole przechowujące treść notyfikacji, oznaczone adnotacją @Column(name="message").
 * timestamp: Pole przechowujące znacznik czasu notyfikacji, oznaczone adnotacją @Column(name="timestamp"). Jest to obiekt typu LocalDateTime, który reprezentuje datę i czas.
 * Klasa Notification posiada konstruktory, które pozwalają na ustawienie wartości pól podczas tworzenia obiektów. Posiada również zestaw getterów i setterów umożliwiających dostęp do pól encji.
 */
@Entity
@Table(name="NOTIFICATION")
public class Notification {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="event_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Event event;
    @Column(name="title")
    private String title;

    @Column(name="message")
    private String message;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    public Notification(Event event, String title, String message, LocalDateTime timestamp) {
        this.event = event;
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Notification() {

    }

    // Getters and setters

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
