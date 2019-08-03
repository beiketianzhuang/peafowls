package com.lchen.funnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FunnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnelApplication.class, args);
    }

}
