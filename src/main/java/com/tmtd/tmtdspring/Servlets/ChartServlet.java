package com.tmtd.tmtdspring.Servlets;

import com.tmtd.tmtdspring.Controllers.DrinkController;
import com.tmtd.tmtdspring.Controllers.TaskController;
import com.tmtd.tmtdspring.Models.Drink;
import com.tmtd.tmtdspring.Models.Task;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@WebServlet(name = "ChartServlet", value = "/ChartServlet")
public class ChartServlet extends HttpServlet {
    @Autowired
    private DrinkController drinkController;
    @Autowired
    private TaskController taskController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current date
//        LocalDate currentDate = LocalDate.now();

        //for testing purposes
        LocalDate currentDate = LocalDate.parse("2023-04-05");

        // Get the first day of the current month
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // Get the last day of the current month
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Get the first day of the current week (assuming Monday as the first day of the week)
        LocalDate firstDayOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Get the last day of the current week (assuming Monday as the first day of the week)
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        //GET DATA FROM CONTROLLERS
        //id for tests - later use current user id from ?session?
        Long id = Long.valueOf(404);
        ResponseEntity <List<Drink>> drinkMonth= drinkController.getUsersDrinksFromTime(id,firstDayOfMonth.minusDays(1),lastDayOfMonth.plusDays(1));
        ResponseEntity <List<Drink>> drinkWeek= drinkController.getUsersDrinksFromTime(id,firstDayOfWeek.minusDays(1),lastDayOfWeek.plusDays(1));
        ResponseEntity <List<Task>> taskMonth= taskController.getTasksFromTimeFrame(id,firstDayOfMonth.minusDays(1),lastDayOfMonth.plusDays(1));
        ResponseEntity <List<Task>> taskWeek= taskController.getTasksFromTimeFrame(id,firstDayOfWeek.minusDays(1),lastDayOfWeek.plusDays(1));

        //SAVE DATA TO SERVLET CONTEXT SO THE CHARTS.HTML CAN USE THEM
        ServletContext context = request.getServletContext();
        context.setAttribute("drinkMonth",drinkMonth);
        context.setAttribute("drinkWeek",drinkWeek);
        context.setAttribute("taskMonth",taskMonth);
        context.setAttribute("taskWeek",taskWeek);

        //REDIRECT TO CHARS.HTML PAGE TO DISPLAY CHARTS
        response.setContentType("text/html");
        response.sendRedirect("/charts.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
