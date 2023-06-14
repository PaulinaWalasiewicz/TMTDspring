package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *Klasa TaskRepository jest interfejsem rozszerzającym interfejs JpaRepository z parametrami Task i Long. JpaRepository jest interfejsem dostarczanym przez Spring Data JPA i zapewnia podstawowe operacje CRUD (Create, Read, Update, Delete) dla encji.
 *
 * Interfejs TaskRepository definiuje dodatkowe metody specyficzne dla encji Task. Są to:
 *
 * findByUserId(Long user_id): Metoda służąca do pobierania listy zadań na podstawie identyfikatora użytkownika. Zwraca listę zadań powiązanych z określonym użytkownikiem.
 * deleteByUserId(long user_id): Metoda służąca do usuwania zadań na podstawie identyfikatora użytkownika. Usuwa wszystkie zadania powiązane z określonym użytkownikiem.
 * Dodatkowo, dzięki rozszerzeniu JpaRepository, interfejs ten dziedziczy wszystkie podstawowe metody do operacji na encjach, takie jak save, findById, findAll, delete, itp.
 *
 * Dzięki temu, że TaskRepository rozszerza JpaRepository, możesz używać tego interfejsu do wykonywania różnych operacji na encjach Task, takich jak zapisywanie, pobieranie, aktualizowanie i usuwanie. Spring Data JPA automatycznie generuje implementacje tych metod na podstawie nazw i typów parametrów.
 */
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long user_id);
    @Transactional
    void deleteByUserId(long user_id);
}
