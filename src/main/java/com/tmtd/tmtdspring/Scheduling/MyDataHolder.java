package com.tmtd.tmtdspring.Scheduling;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa MyDataHolder jest oznaczona adnotacją @Component, która informuje framework Spring, że ta klasa jest komponentem zarządzanym przez kontener aplikacji. Oznacza to, że obiekty tej klasy mogą być tworzone, zarządzane i wstrzykiwane przez Spring.
 *
 * Klasa MyDataHolder zawiera prywatne pole messages, które jest listą napisów. Klasa ta dostarcza metody do manipulacji tym polem. Metoda getMessages() zwraca listę wiadomości, a metoda setMessages() ustawia nową listę wiadomości.
 *
 * Jest to prosty przykład klasy, która działa jako trzymacz danych (data holder) i przechowuje listę wiadomości. Poprzez oznaczenie tej klasy adnotacją @Component, można ją zarejestrować w kontekście Spring i używać jej w innych komponentach poprzez wstrzykiwanie zależności.
 */
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
