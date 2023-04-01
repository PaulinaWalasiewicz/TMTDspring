package com.tmtd.tmtdspring;

import com.tmtd.tmtdspring.Scheduling.MyDataHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MyDataHolder dataHolder() {
        return new MyDataHolder();
    }
}
