package com.tmtd.tmtdspring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordcontroller {
    @RequestMapping("/hi")
    public String helloWord(){
        return  "Hello Word from Spring Boot";
    }
}
