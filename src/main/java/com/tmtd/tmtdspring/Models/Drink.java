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
    @JsonIgnore
    private User user;

    @Column(name = "count")
    private int count;

    @Column(name = "limit")
    private float limit;

    @Column(name = "drink_type")
    private String drink_type;

    @Column(name = "unit")
    private String unitt;

    @Column(name="drink_date" , nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate drink_date;

    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    @JoinColumn(name="type",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DrinkType type;
    @ManyToOne(fetch=FetchType.EAGER,optional = false)
    @JoinColumn(name="liquid_unit",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LiquidUnit unit;

    public Drink(User user, int count, float limit, DrinkType drinkType, LiquidUnit unit,LocalDate drink_date) {
        this.user = user;
        this.count = count;
        this.limit = limit;
        this.type = drinkType;
        this.unit = unit;
        this.drink_type = drinkType.getType();
        this.unitt = unit.getUnit();
        this.drink_date = drink_date;
    }

    public Drink() {

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

    public DrinkType getDrinkType() {
        return type;
    }

    public void setDrinkType(DrinkType type) {
        this.type = type;
    }

    public LiquidUnit getUnit() {
        return unit;
    }

    public void setUnit(LiquidUnit unit) {
        this.unit = unit;
    }
    public void setDrink_date(LocalDate drink_date){this.drink_date=drink_date;}
    public LocalDate getDrink_date(){return drink_date;}
    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", user=" + user +
                ", count=" + count +
                ", limit=" + limit +
                ", type=" + type +
                ", unit=" + unit +
                '}';
    }
}
