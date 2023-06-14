package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *Klasa DrinkRepository jest interfejsem rozszerzającym interfejs JpaRepository z parametrami Drink i Long. JpaRepository jest interfejsem dostarczanym przez Spring Data JPA i zapewnia podstawowe operacje CRUD (Create, Read, Update, Delete) dla encji.
 *
 * Metoda findByUserId jest domyślną implementacją metody zapytania, która pozwala wyszukiwać napoje na podstawie identyfikatora użytkownika (user_id). Metoda ta zwraca listę obiektów klasy Drink, które są powiązane z danym użytkownikiem.
 *
 * Spring Data JPA automatycznie generuje implementację tej metody na podstawie jej nazwy i typów parametrów. Wyszukuje ona w bazie danych napoje, których pole user_id odpowiada podanemu identyfikatorowi użytkownika.
 */
public interface DrinkRepository extends JpaRepository<Drink,Long> {
    List<Drink> findByUserId(Long user_id);


}
