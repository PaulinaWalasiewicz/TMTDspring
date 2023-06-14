package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *Klasa NotificationRepository jest interfejsem rozszerzającym interfejs JpaRepository z parametrami Notification i Long. JpaRepository jest interfejsem dostarczanym przez Spring Data JPA i zapewnia podstawowe operacje CRUD (Create, Read, Update, Delete) dla encji.
 *
 * Interfejs NotificationRepository nie definiuje żadnych dodatkowych metod. Dzięki rozszerzeniu JpaRepository, interfejs ten dziedziczy wszystkie podstawowe metody do operacji na encjach, takie jak save, findById, findAll, delete, itp.
 *
 * Dzięki temu, że NotificationRepository rozszerza JpaRepository, możesz używać tego interfejsu do wykonywania różnych operacji na encjach Notification, takich jak zapisywanie, pobieranie, aktualizowanie i usuwanie. Spring Data JPA automatycznie generuje implementacje tych metod na podstawie nazw i typów parametrów.
 */
public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
