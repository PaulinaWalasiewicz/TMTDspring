package com.tmtd.tmtdspring.Models;


import jakarta.persistence.*;

/**
 *Klasa User jest adnotowana jako @Entity i mapowana do tabeli o nazwie "USERR" za pomocą adnotacji @Table(name="USERR").
 *
 * Posiada następujące pola:
 *
 * id: Pole identyfikatora encji, które jest oznaczone adnotacją @Id. Jest generowane automatycznie za pomocą strategii GenerationType.TABLE.
 * username: Pole przechowujące nazwę użytkownika, oznaczone adnotacją @Column(name="username", nullable = false).
 * password: Pole przechowujące hasło użytkownika, oznaczone adnotacją @Column(name="password", nullable = false).
 * email: Pole przechowujące adres e-mail użytkownika, oznaczone adnotacją @Column(name="email", nullable = false).
 * firstName: Pole przechowujące imię użytkownika, oznaczone adnotacją @Column(name="first_name", nullable = false).
 * lastName: Pole przechowujące nazwisko użytkownika, oznaczone adnotacją @Column(name="last_name", nullable = false).
 * Klasa User posiada konstruktory, które pozwalają na ustawienie wartości pól podczas tworzenia obiektów. Posiada również zestaw getterów i setterów umożliwiających dostęp do pól encji.
 */
@Entity
@Table(name="USERR")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;
    //Constructor


    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return "User [id="+id+", first_name="+firstName+", fast_name="+lastName+", email="+email+", password="+password+"]";
    }
}

