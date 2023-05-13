package com.tmtd.tmtdspring.Models;

public enum LiquidUnit {
    LITER("Liter"),
    MILLILITER("Milliliter"),
    GALLON("Gallon"),
    OUNCE("Ounce"),
    PINT("Pint");

    private final String displayName;

    LiquidUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
