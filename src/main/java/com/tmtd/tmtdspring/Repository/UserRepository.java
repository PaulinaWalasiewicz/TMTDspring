package com.tmtd.tmtdspring.Repository;
import java.util.List;

import com.tmtd.tmtdspring.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *Klasa UserRepository jest interfejsem oznaczonym adnotacją @Repository, który rozszerza interfejs JpaRepository z parametrami User i Long. Adnotacja @Repository informuje Spring, że ten interfejs jest komponentem repozytorium, który obsługuje dostęp do danych dla encji User.
 *
 * Interfejs UserRepository definiuje kilka metod dostępu do danych związanych z encją User. Są to:
 *
 * findByEmail(String email): Metoda służąca do pobierania listy użytkowników na podstawie adresu e-mail. Zwraca listę użytkowników, którzy mają określony adres e-mail.
 * Dodatkowo, dzięki rozszerzeniu JpaRepository, interfejs ten dziedziczy wszystkie podstawowe metody do operacji na encjach, takie jak save, findById, findAll, delete, itp.
 *
 * Dzięki temu, że UserRepository rozszerza JpaRepository, możesz używać tego interfejsu do wykonywania różnych operacji na encjach User, takich jak zapisywanie, pobieranie, aktualizowanie i usuwanie. Spring Data JPA automatycznie generuje implementacje tych metod na podstawie nazw i typów parametrów.
 */
@Repository
public interface UserRepository extends  JpaRepository<User,Long>{
    List<User> findByEmail(String email);
}
