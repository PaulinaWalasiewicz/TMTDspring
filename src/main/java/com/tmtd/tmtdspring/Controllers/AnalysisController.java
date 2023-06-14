package com.tmtd.tmtdspring.Controllers;


import com.tmtd.tmtdspring.Models.Drink;
import com.tmtd.tmtdspring.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 *
 Klasa AnalysisController jest kontrolerem odpowiadającym za analizy danych użytkownika. Ma ona dwa pola oznaczone adnotacją @Autowired: drinkController i taskController. Te pola są wstrzykiwane przez mechanizm Springa i odnoszą się do innych kontrolerów, DrinkController i TaskController odpowiednio. Wstrzykiwanie zależności pozwala na łatwe korzystanie z funkcjonalności tych kontrolerów w obrębie AnalysisController.

 Następnie znajduje się kilka zmiennych, które mają na celu uzyskanie danych dotyczących dat. Mamy currentDate, której wartość to bieżąca data uzyskana za pomocą LocalDate.now(). Następnie mamy firstDayOfMonth i lastDayOfMonth, które określają pierwszy i ostatni dzień bieżącego miesiąca na podstawie currentDate. Kolejne zmienne, firstDayOfWeek i lastDayOfWeek, określają pierwszy i ostatni dzień bieżącego tygodnia, przyjmując poniedziałek jako pierwszy dzień tygodnia. Te zmienne są wykorzystywane w endpointach do określania zakresów czasowych dla pobieranych danych.

 Następnie mamy cztery metody oznaczone adnotacją @GetMapping i ścieżkami: /DrinkWeek, /DrinkMonth, /TaskWeek i /TaskMonth. Każda z tych metod zwraca obiekt ResponseEntity, który zawiera odpowiedź HTTP. Metody te wywołują metody z drinkController i taskController, aby pobrać odpowiednie dane dla określonych zakresów czasowych. Na przykład, metoda drinkWeek() wywołuje getUsersDrinksFromTime(id, firstDayOfWeek.minusDays(1), lastDayOfWeek.plusDays(1)) na obiekcie drinkController, aby pobrać listę napojów dla bieżącego tygodnia. Otrzymane dane są następnie umieszczone w obiekcie ResponseEntity i zwracane jako odpowiedź.

 Wszystkie endpointy są dostępne pod ścieżką bazową /api/analysisData, po której następuje dodatkowy segment ścieżki dla każdej metody, np. /DrinkWeek, /DrinkMonth, itd.
 */

@CrossOrigin(origins = "http://localhost:8080")

@RestController
@RequestMapping("/api/analysisData")
public class AnalysisController {
    @Autowired
    private DrinkController drinkController;

    @Autowired
    private TaskController taskController;

    // Get the current date
        LocalDate currentDate = LocalDate.now();

    //for testing purposes
//    LocalDate currentDate = LocalDate.parse("2023-04-05");

    // Get the first day of the current month
    LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

    // Get the last day of the current month
    LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

    // Get the first day of the current week (assuming Monday as the first day of the week)
    LocalDate firstDayOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    // Get the last day of the current week (assuming Monday as the first day of the week)
    LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

    //id for tests - later use current user id from ?session?
    Long id = Long.valueOf(404);
    @GetMapping("/DrinkWeek")
    public ResponseEntity<ResponseEntity<List<Drink>>> drinkWeek(){
        ResponseEntity <List<Drink>> drinkWeek= drinkController.getUsersDrinksFromTime(id,firstDayOfWeek.minusDays(1),lastDayOfWeek.plusDays(1));
        return  ResponseEntity.ok(drinkWeek);
    }
    @GetMapping("/DrinkMonth")
    public ResponseEntity<ResponseEntity<List<Drink>>> drinkMonth(){
        ResponseEntity <List<Drink>> drinkMonth= drinkController.getUsersDrinksFromTime(id,firstDayOfMonth.minusDays(1),lastDayOfMonth.plusDays(1));
        return  ResponseEntity.ok(drinkMonth);
    }
    @GetMapping("/TaskWeek")
    public ResponseEntity<ResponseEntity<List<Task>>> taskWeek(){
        ResponseEntity <List<Task>> taskWeek= taskController.getTasksFromTimeFrame(id,firstDayOfWeek.minusDays(1),lastDayOfWeek.plusDays(1));
        return  ResponseEntity.ok(taskWeek);
    }
    @GetMapping("/TaskMonth")
    public ResponseEntity<ResponseEntity<List<Task>>> taskMonth(){
        ResponseEntity <List<Task>> taskMonth= taskController.getTasksFromTimeFrame(id,firstDayOfMonth.minusDays(1),lastDayOfMonth.plusDays(1));
        return  ResponseEntity.ok(taskMonth);
    }
}
