package com.tmtd.tmtdspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Klasa TmtDspringApplication jest główną klasą aplikacji Spring Boot. Jest oznaczona adnotacją @SpringBootApplication, która integruje trzy adnotacje: @Configuration, @EnableAutoConfiguration i @ComponentScan. Dzięki temu, klasa ta pełni rolę konfiguracyjną, umożliwia automatyczną konfigurację Spring Boot i skanowanie komponentów w określonym pakiecie.
 *
 * Adnotacja @EnableScheduling włącza obsługę planowania zadań w aplikacji. Pozwala na używanie adnotacji @Scheduled do oznaczania metod, które będą wykonywane cyklicznie zgodnie z określonym harmonogramem.
 *
 * Adnotacja @ServletComponentScan skanuje komponenty servletów, filtry i nasłuchiwacze, które są oznaczone adnotacjami @WebServlet, @WebFilter i @WebListener. Umożliwia automatyczną konfigurację tych komponentów w aplikacji Spring.
 *
 * Metoda main() jest punktem wejścia do aplikacji. Uruchamia aplikację Spring Boot poprzez wywołanie metody run() klasy SpringApplication z odpowiednimi argumentami.
 */
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
