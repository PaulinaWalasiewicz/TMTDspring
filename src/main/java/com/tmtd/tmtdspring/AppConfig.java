package com.tmtd.tmtdspring;

import com.tmtd.tmtdspring.Scheduling.MyDataHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *Klasa AppConfig jest oznaczona adnotacją @Configuration i jest używana do konfiguracji komponentów w kontekście aplikacji Spring.
 *
 * Metoda dataHolder() jest oznaczona adnotacją @Bean i jest odpowiedzialna za tworzenie i konfigurację bean-a MyDataHolder. Bean ten zostanie zarejestrowany w kontekście aplikacji Spring i będzie dostępny do wstrzykiwania zależności w innych komponentach.
 *
 * Klasa AppConfig może być używana do definiowania dodatkowych konfiguracji w kontekście aplikacji Spring. Może zawierać dodatkowe metody oznaczone adnotacją @Bean, które definiują i konfigurują inne beany, zarządzane przez kontener Spring.
 */
@Configuration
public class AppConfig {
    @Bean
    public MyDataHolder dataHolder() {
        return new MyDataHolder();
    }
}
