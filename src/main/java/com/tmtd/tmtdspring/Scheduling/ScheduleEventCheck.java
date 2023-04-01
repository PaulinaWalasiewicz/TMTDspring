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
@Component

public class ScheduleEventCheck  {
    @Autowired
    private EventController eventController;
    @Autowired
    private MyDataHolder myDataHolder;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //schedule rate in which this function repeats itself
    @Scheduled(fixedRate = 50000)
    // 5000 = 5 sec
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
            //Search for events that are in 30 minutes or less
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



