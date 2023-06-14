package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

/**
 *Klasa Event jest oznaczona adnotacją @Entity, co oznacza, że reprezentuje encję, czyli obiekt, który ma swoje odzwierciedlenie w bazie danych. Adnotacja @Table(name="EVENT") wskazuje na nazwę tabeli, w której będą przechowywane dane obiektów klasy Event.
 *
 * Klasa Event ma następujące pola:
 *
 * id: Pole oznaczone adnotacją @Id jest identyfikatorem encji. Jest generowane automatycznie (@GeneratedValue) i korzysta z typu GenerationType.TABLE, co oznacza, że identyfikator jest generowany na podstawie wartości z tabeli sekwencji.
 * user: Pole oznaczone adnotacją @ManyToOne oznacza relację wielu do jednego z inną encją User. Relacja ta jest leniwa (fetch=FetchType.LAZY), co oznacza, że dane użytkownika nie są wczytywane automatycznie przy pobieraniu obiektu Event. Pole user_id w bazie danych jest kluczem obcym dla tabeli User.
 * title: Pole przechowuje tytuł (nazwę) wydarzenia. Jest oznaczone adnotacją @Column(name="title", nullable = false), co oznacza, że nie może być puste.
 * startTime i endTime: Pola przechowują datę i godzinę rozpoczęcia oraz zakończenia wydarzenia. Są oznaczone adnotacją @Column i korzystają z adnotacji @JsonFormat, aby określić format daty i godziny podczas serializacji i deserializacji obiektu.
 * description: Pole przechowuje opis wydarzenia. Jest opcjonalne, czyli może być puste.
 * Klasa Event zawiera konstruktory, gettery i settery dla wszystkich pól, co umożliwia manipulację danymi obiektów. Metoda toString() została przesłonięta, aby zwracać reprezentację obiektu w postaci tekstowej.
 *
 * Klasa Event reprezentuje wydarzenie w systemie i jest powiązana z użytkownikiem (encją User) poprzez relację wielu do jednego.
 */
@Entity
@Table(name="EVENT")
public class Event {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @Column(name="title",nullable = false)
    private String title;

    @Column(name="start_time",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Column(name="end_time",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;


    @Column(name = "description")
    private String description;
    //Constructor


    public Event(User user, String title, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.user = user;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public Event(){}

    // Getters and setters

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description=" + description +
                '}';
    }
}

