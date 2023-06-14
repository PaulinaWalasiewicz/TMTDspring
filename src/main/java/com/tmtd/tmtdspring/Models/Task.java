package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 *Klasa Task jest adnotowana jako @Entity, co oznacza, że reprezentuje encję w bazie danych. Jest mapowana do tabeli o nazwie "TASK" za pomocą adnotacji @Table(name="TASK").
 *
 * Posiada następujące pola:
 *
 * id: Pole identyfikatora encji, które jest oznaczone adnotacją @Id. Jest generowane automatycznie za pomocą strategii GenerationType.TABLE.
 * title: Pole przechowujące tytuł zadania, oznaczone adnotacją @Column(name="title").
 * description: Pole przechowujące opis zadania, oznaczone adnotacją @Column(name = "description").
 * dueDate: Pole przechowujące datę i czas terminu wykonania zadania, oznaczone adnotacją @Column(name="due_date"). Jest to obiekt typu LocalDateTime, który reprezentuje datę i czas.
 * completed: Pole logiczne wskazujące, czy zadanie zostało wykonane, oznaczone adnotacją @Column(name="completed").
 * user: Pole reprezentujące użytkownika (User), do którego przypisane jest zadanie. Jest to pole klucza obcego oznaczone adnotacją @ManyToOne. W bazie danych jest mapowane jako pole user_id, które nie może być puste (nullable = false). Powiązanie jest typu leniwego (fetch=FetchType.LAZY), co oznacza, że dane użytkownika będą pobierane tylko wtedy, gdy będą potrzebne.
 * category: Pole reprezentujące kategorię zadania (Category), oznaczone adnotacją @Enumerated(EnumType.STRING). Jest to pole klasy wyliczeniowej, które przechowuje kategorię zadania.
 * priority: Pole przechowujące priorytet zadania, oznaczone adnotacją @Column(name = "priority", nullable = false).
 * Klasa Task posiada konstruktory, które pozwalają na ustawienie wartości pól podczas tworzenia obiektów. Posiada również zestaw getterów i setterów umożliwiających dostęp do pól encji.
 *
 * Klasa Task jest używana do przechowywania informacji o zadaniach w systemie. Zawiera pola takie jak tytuł, opis, termin wykonania, status ukończenia, przypisany użytkownik, kategoria i priorytet.
 */
@Entity
@Table(name="TASK")
public class Task {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(name="title")
    private String title;


    @Column(name = "description")
    private String description;

    @Column(name="due_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;

    @Column(name="completed")
    private boolean completed;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "priority", nullable = false)
    private String priority;


    public Task(Long id, String title, String description, LocalDateTime dueDate, boolean completed, User user, Category category, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.user = user;
        this.category = category;
        this.priority = priority;
    }

    public Task() {

    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task {" +
                "id=" + id +
                ", user=" + user.getId() +
                ", title='" + title  +
                ", description=" + description +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                ", category=" + category +
                ", priority=" + priority +
                '}';

    }
}

