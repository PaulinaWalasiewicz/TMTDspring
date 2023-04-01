package com.tmtd.tmtdspring;

import com.tmtd.tmtdspring.Scheduling.ScheduleEventCheck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableScheduling
@RequestMapping
public class TmtDspringApplication {

    public static void main(String[] args) {

        SpringApplication.run(TmtDspringApplication.class, args);

    }
    @GetMapping
    public String hello(){
        return "Hello";
    }
}
