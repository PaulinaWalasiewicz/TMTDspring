package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Scheduling.MyDataHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Klasa DataHoldercontroller jest to prosty kontroler, który udostępnia listę nadchodzących wydarzeń poprzez jedno zapytanie GET. Klasa ta ma jedno pole oznaczone adnotacją @Autowired: myDataHolder. To pole jest wstrzykiwane przez mechanizm Springa i odnosi się do innego komponentu, MyDataHolder. Wstrzykiwanie zależności pozwala na łatwe korzystanie z funkcjonalności MyDataHolder w obrębie DataHoldercontroller.
 *
 * Następnie mamy metodę oznaczoną adnotacją @GetMapping, która obsługuje zapytania typu GET. Ponieważ nie podano żadnej konkretnej ścieżki, metoda będzie obsługiwać zapytania podstawowe dla ścieżki bazowej /api/upcomingevents.
 *
 * Metoda getUpcomingEvetns() zwraca listę łańcuchów znaków (List<String>) przez wywołanie metody getMessages() na obiekcie myDataHolder. To oznacza, że metoda getUpcomingEvetns() zwraca dane przechowywane w myDataHolder jako listę łańcuchów znaków, reprezentującą nadchodzące wydarzenia.
 *
 * Klasa DataHoldercontroller obsługuje jedno zapytanie GET, które zwraca listę nadchodzących wydarzeń z myDataHolder pod ścieżką /api/upcomingevents.
 */
@CrossOrigin(origins = "http://localhost:8080")

@RestController
@RequestMapping("/api/upcomingevents")
public class DataHoldercontroller {
    @Autowired
    private MyDataHolder myDataHolder;
    @GetMapping
    public List<String> getUpcomingEvetns(){
        return myDataHolder.getMessages();
    }
}
