package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
