package com.tmtd.tmtdspring.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "LIQUID_UNITS")
public class LiquidUnit {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    @Column(name = "unit")
    private String unit;

    public LiquidUnit(String unit) {
        this.unit = unit;
    }
    public LiquidUnit(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
//    LITER,
//    GALLON,
//    QUART,
//    PINT,
//    CUP,
//    FLUID_OUNCE,
//    MILLILITER

