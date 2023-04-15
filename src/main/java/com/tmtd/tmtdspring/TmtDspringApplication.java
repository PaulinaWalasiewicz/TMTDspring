package com.tmtd.tmtdspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
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
