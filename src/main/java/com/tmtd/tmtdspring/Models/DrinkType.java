package com.tmtd.tmtdspring.Models;

import jakarta.persistence.*;

@Entity
@Table(name="DRINK_TYPE")
public class DrinkType {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(name = "type")
    private String type;

//    WATER,
//    COFFEE,
//    ENERGY_DRINK,


    public DrinkType(String content) {
        this.type = content;
    }

    public DrinkType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String content) {
        this.type = content;
    }
}
