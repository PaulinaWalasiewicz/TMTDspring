package com.tmtd.tmtdspring.Scheduling;

import com.tmtd.tmtdspring.Controllers.EventController;
import com.tmtd.tmtdspring.Models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.script.*;
@Component
public class ScheduleEventCheck  {
    @Autowired
    private EventController eventController;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 50000)
    // 5000 = 5 sec
    public  void  reportCurrentTime(){
        System.out.println("The time is now " + dateFormat.format(new Date()));
        Long id = Long.valueOf(404);
        try {
            ResponseEntity<List<Event>> evens = eventController.getAllEventsByUserId(id);
//            System.out.println(evens);
            if(evens.hasBody()) {
                if(evens.getBody().size()>0){
                    SearchforCurrentDate(evens.getBody());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    private void SearchforCurrentDate(List<Event> events) throws ScriptException {

        LocalDate dateNow = LocalDate.now();


        List<Event> filtred = events.stream()
                .filter(event -> event.getStartTime().toLocalDate().isEqual(dateNow))
                .toList();
//        System.out.println("Filtered: " + filtred);

        if(filtred.size()>0){
            LocalTime timeNow = LocalTime.now();
            for(Event e:filtred){
                Duration duration = Duration.between(timeNow,e.getStartTime().toLocalTime());
                System.out.println("duration is:" + duration.toMinutes());
                if(duration.toMinutes() <= 60){
                    System.out.println(e.getTitle());
                    ShowNotification(duration,e.getTitle());
                }
            }
        }

    }
    private void ShowNotification(Duration d , String title) throws ScriptException {

        // create a script engine manager
        ScriptEngineManager manager = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = manager.getEngineByName("Nashorn");
        // evaluate the script
        String message = "UPCOMING EVENT "+title+" IN "+d.toMinutes() + " min";
        String script = """
                        function showNotification(title, options) {if ("Notification" in window) {if (Notification.permission === "granted") {var notification = new Notification(title, options);
                    } else if (Notification.permission !== "denied") {
                        Notification.requestPermission().then(function(permission) {
                            if (permission === "granted") {var notification = new Notification(title, options);}}); }}}""";
        try {
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            inv.invokeFunction("showNotification", "Notification", message);
        }catch (NoSuchMethodException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

