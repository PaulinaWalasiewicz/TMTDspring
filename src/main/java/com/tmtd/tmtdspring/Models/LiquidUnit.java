package com.tmtd.tmtdspring.Models;

/**
 *Klasa LiquidUnit jest typem wyliczeniowym (enum) reprezentującym jednostki miary dla płynów. Posiada następujące wartości:
 *
 * LITER: Reprezentuje jednostkę litra.
 * MILLILITER: Reprezentuje jednostkę mililitra.
 * GALLON: Reprezentuje jednostkę galonu.
 * OUNCE: Reprezentuje jednostkę uncji.
 * PINT: Reprezentuje jednostkę pinty.
 * Każda wartość enum ma skojarzoną z nią nazwę wyświetlaną (displayName). Nazwy te są przekazywane do konstruktora enuma, który przypisuje je do pola displayName.
 *
 * Klasa LiquidUnit posiada metodę getDisplayName(), która zwraca nazwę wyświetlaną dla danej jednostki miary. Dzięki temu można uzyskać czytelne reprezentacje tekstowe jednostek miary płynów, na przykład "Liter", "Milliliter", itd.
 *
 * Klasa LiquidUnit służy do ograniczenia i reprezentacji dostępnych jednostek miary dla płynów w systemie. Może być używana wraz z innymi klasami lub encjami, które wymagają określenia jednostki miary dla płynów.
 */
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
