package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *Klasa EventRepository jest interfejsem rozszerzającym interfejs JpaRepository z parametrami Event i Long. JpaRepository jest interfejsem dostarczanym przez Spring Data JPA i zapewnia podstawowe operacje CRUD (Create, Read, Update, Delete) dla encji.
 *
 * Interfejs EventRepository zawiera dwie dodatkowe metody:
 *
 * Metoda findByUserId jest domyślną implementacją metody zapytania, która pozwala wyszukiwać wydarzenia na podstawie identyfikatora użytkownika (user_id). Metoda zwraca listę obiektów klasy Event, które są powiązane z danym użytkownikiem.
 *
 * Metoda deleteByUserId jest oznaczona adnotacją @Transactional, co oznacza, że operacja usuwania będzie wykonywana w ramach transakcji. Metoda usuwa wszystkie wydarzenia powiązane z danym identyfikatorem użytkownika (user_id).
 *
 * Spring Data JPA automatycznie generuje implementacje tych metod na podstawie ich nazw i typów parametrów.
 */
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByUserId(Long user_id);

    @Transactional
    void deleteByUserId(long user_id);
}
