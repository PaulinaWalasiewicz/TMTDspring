package com.tmtd.tmtdspring.Scheduling;

import com.tmtd.tmtdspring.Controllers.EventController;
import com.tmtd.tmtdspring.Models.Event;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.script.*;

/**
 *Klasa ScheduleEventCheck nie jest oznaczona adnotacją @Component, ale jest to klasa, która może być wykorzystywana jako komponent w kontenerze aplikacji Spring.
 *
 * Klasa ScheduleEventCheck wykonuje określone zadanie cyklicznie z wykorzystaniem mechanizmu harmonogramowania dostępnego w Spring Framework. Zawiera pola EventController i MyDataHolder, które są wstrzykiwane przez Spring za pomocą adnotacji @Autowired.
 *
 * Metoda reportCurrentTime() jest oznaczona adnotacją @Scheduled i jest uruchamiana z określoną częstotliwością. Wewnątrz tej metody jest wykonywana logika, która pobiera listę wydarzeń za pomocą EventController i filtruje te wydarzenia, aby znaleźć te, które odnoszą się do bieżącej daty i czasu. Informacje o tych wydarzeniach są przechowywane w liście wiadomości messageList, a następnie ustawiane w MyDataHolder.
 *
 * Metoda SearchforCurrentDate() jest odpowiedzialna za filtrowanie wydarzeń na podstawie bieżącej daty i czasu. Wydarzenia, które mają miejsce w ciągu 60 minut od bieżącego czasu, są dodawane do listy wiadomości.
 *
 * Klasa ScheduleEventCheck może być zarejestrowana jako komponent w kontekście Spring i jej zadanie będzie wykonywane automatycznie zgodnie z harmonogramem.
 */
@Component

public class ScheduleEventCheck  {
    @Autowired
    private EventController eventController;
    @Autowired
    private MyDataHolder myDataHolder;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //schedule rate in which this function repeats itself
    @Scheduled(fixedRate = 600000)
    // 5000 = 5 sec
    // 600000 = 10 minutes
    public  void  reportCurrentTime(){
        System.out.println("The time is now " + dateFormat.format(new Date()));
        Long id = Long.valueOf(404);
        try {
            //Get events for user
            ResponseEntity<List<Event>> evens = eventController.getAllEventsByUserId(id);
//            System.out.println(evens);
            if(evens.hasBody()) {
                if(evens.getBody().size()>0){
                    //Get events with current date
                    SearchforCurrentDate(evens.getBody());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    private void SearchforCurrentDate(List<Event> events) throws ScriptException {

        List<String> messageList = new ArrayList<>();
        //get current date
        LocalDate dateNow = LocalDate.now();

        //Filter events, result = evetns for today
        List<Event> filtred = events.stream()
                .filter(event -> event.getStartTime().toLocalDate().isEqual(dateNow))
                .toList();
//        System.out.println("Filtered: " + filtred);

        if(filtred.size()>0){
            LocalTime timeNow = LocalTime.now();
            //Search for events that are in 60 minutes or less
            for(Event e:filtred){
                Duration duration = Duration.between(timeNow,e.getStartTime().toLocalTime());
                System.out.println("duration is:" + duration.toMinutes());
                if(duration.toMinutes() <= 60){
                    System.out.println(e.getTitle());
                    messageList.add("UPCOMING EVENT " + e.getTitle() + " IN " + duration.toMinutes() + " min");
                }
            }
            myDataHolder.setMessages(messageList);
        }
    }

    }



