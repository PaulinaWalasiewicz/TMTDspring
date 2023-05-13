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
