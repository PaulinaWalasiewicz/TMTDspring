package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 *Klasa Drink jest oznaczona adnotacją @Entity i reprezentuje encję (tabelę) w bazie danych. Adnotacja @Table(name = "DRINK") wskazuje, że encja ma być mapowana do tabeli o nazwie "DRINK". Każde pole klasy Drink jest mapowane na kolumnę w tabeli, a adnotacje używane w klasie dostarczają dodatkowych informacji dotyczących mapowania.
 *
 * Poniżej opis poszczególnych pól i adnotacji w klasie Drink:
 *
 * @Id: Oznacza pole id jako klucz główny encji.
 * @Column(name = "id"): Określa, że pole id ma być mapowane na kolumnę o nazwie "id" w tabeli.
 * @GeneratedValue(strategy = GenerationType.TABLE): Definiuje strategię generowania wartości dla pola id. W tym przypadku używana jest strategia oparta na tabeli.
 * @ManyToOne(fetch = FetchType.EAGER, optional = false): Określa relację wiele-do-jednego z inną encją (klasa User). Pole user reprezentuje właściciela danego rekordu w tabeli Drink.
 * @JoinColumn(name = "user_id", nullable = false): Określa, że pole user ma być mapowane na kolumnę "user_id" w tabeli i jest wymagane (not null).
 * @OnDelete(action = OnDeleteAction.CASCADE): Definiuje akcję kaskadowego usuwania. W przypadku usunięcia rekordu z tabeli User, powiązane rekordy z tabeli Drink zostaną również usunięte.
 * @Column(name = "count"): Mapuje pole count na kolumnę "count" w tabeli.
 * @Column(name = "drink_limit"): Mapuje pole limit na kolumnę "drink_limit" w tabeli.
 * @Enumerated(EnumType.STRING): Określa sposób mapowania enumów DrinkType i LiquidUnit na kolumny w tabeli. Enumy zostaną zapisane jako wartości tekstowe.
 * @Column(name = "drink_type"): Mapuje pole drink_type na kolumnę "drink_type" w tabeli.
 * @Column(name = "unit"): Mapuje pole unit na kolumnę "unit" w tabeli.
 * @Column(name = "drink_date", nullable = false): Mapuje pole drink_date na kolumnę "drink_date" w tabeli. Jest oznaczone jako wymagane (not null).
 * @JsonFormat(pattern = "yyyy-MM-dd"): Określa format daty w polu drink_date podczas serializacji do formatu JSON.
 * Klasa Drink zawiera również konstruktory, metody dostępowe (gettery i settery) oraz nadpisuje metodę toString().
 *
 * Klasa Drink reprezentuje model danych dla tabeli "DRINK" i umożliwia operacje związane z encją Drink w kontekście bazy danych.
 */
@Entity
@Table(name="DRINK")
public class Drink {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "count")
    private int count;

    @Column(name = "drink_limit")
    private float limit;

    @Enumerated(EnumType.STRING)
    @Column(name = "drink_type")
    private DrinkType drink_type;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private LiquidUnit unit;

    @Column(name="drink_date" , nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate drink_date;

    public Drink(Long id, User user, int count, float limit, DrinkType drink_type, LiquidUnit unit, LocalDate drink_date) {
        this.id = id;
        this.user = user;
        this.count = count;
        this.limit = limit;
        this.drink_type = drink_type;
        this.unit = unit;
        this.drink_date = drink_date;
    }

    public Drink() {

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getLimit() {
        return limit;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public DrinkType getDrink_type() {
        return drink_type;
    }

    public void setDrink_type(DrinkType drink_type) {
        this.drink_type = drink_type;
    }

    public LiquidUnit getUnit() {
        return unit;
    }

    public void setUnit(LiquidUnit unit) {
        this.unit = unit;
    }

    public LocalDate getDrink_date() {
        return drink_date;
    }

    public void setDrink_date(LocalDate drink_date) {
        this.drink_date = drink_date;
    }

    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", user=" + user +
                ", count=" + count +
                ", limit=" + limit +
                ", type=" + drink_type +
                ", unit=" + unit +
                '}';
    }
}
