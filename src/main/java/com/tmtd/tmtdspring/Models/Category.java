package com.tmtd.tmtdspring.Models;

import jakarta.persistence.*;


/**
 *Klasa Category jest publicznym wyliczeniem (enum) reprezentującym kategorie. W przypadku klasy Category, enum zawiera stałe reprezentujące różne kategorie, takie jak "FOOD", "ENTERTAINMENT", "TRAVEL", "HOME", "SCHOOL", "HEALTH", "TECHNOLOGY" i "OTHER".
 *
 * Każda z tych stałych jest reprezentowana jako obiekt typu Category i ma przypisaną nazwę identyfikującą daną kategorię. Można się do nich odwoływać poprzez nazwę enuma, na przykład Category.FOOD, Category.ENTERTAINMENT, itd.
 *
 * Enumy są często używane w celu zdefiniowania zbioru stałych wartości, które są logicznie powiązane ze sobą. W przypadku klasy Category, enum jest używany do określenia kategorii zadań, z którymi można je powiązać. Dzięki temu można precyzyjnie określić kategorię, do której należy dany obiekt Task.
 *
 */

public enum Category {
    FOOD,
    ENTERTAINMENT,
    TRAVEL,
    HOME,
    SHOOL,
    HEALTH,
    TECHNOLOGY,
    OTHER
}