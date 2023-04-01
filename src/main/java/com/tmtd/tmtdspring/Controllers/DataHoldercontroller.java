package com.tmtd.tmtdspring.Controllers;

import com.tmtd.tmtdspring.Scheduling.MyDataHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:8080")

@RestController
@RequestMapping("/api/upcomingevents")
public class DataHoldercontroller {
    @Autowired
    private MyDataHolder myDataHolder;
    @GetMapping
    public List<String> getUpcomingEvetns(){
        return myDataHolder.getMessages();
    }
}
