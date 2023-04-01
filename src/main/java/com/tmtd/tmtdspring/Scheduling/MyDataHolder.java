package com.tmtd.tmtdspring.Scheduling;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyDataHolder {
    private List<String> messages = new ArrayList<>();
    public List<String> getMessages(){
        return messages;
    }
    public void setMessages(List<String> messages){
        this.messages=messages;
    }
}
