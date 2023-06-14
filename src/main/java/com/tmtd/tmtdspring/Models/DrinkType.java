package com.tmtd.tmtdspring.Models;

/**
 *Klasa DrinkType jest typem wyliczeniowym (enum) reprezentującym różne typy napojów.
 *
 * W klasie DrinkType mamy trzy możliwe wartości:
 *
 * Coffee: Reprezentuje typ napoju jako kawę.
 * EnergyDrink: Reprezentuje typ napoju jako energetyczny napój.
 * Water: Reprezentuje typ napoju jako wodę.
 * Klasa DrinkType jest zwykle używana w kontekście klasy Drink, gdzie pole drink_type w klasie Drink przechowuje wartość typu napoju dla danego rekordu.
 *
 * Używanie typu wyliczeniowego DrinkType pozwala na jednoznaczne określenie typu napoju i ogranicza możliwe wartości dla pola drink_type do wyliczonych wartości: "Coffee", "EnergyDrink" lub "Water".
 */
public enum DrinkType {
    Coffee,
    EnergyDrink,
    Water
}
