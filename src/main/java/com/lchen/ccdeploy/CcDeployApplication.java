package com.lchen.ccdeploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CcDeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcDeployApplication.class, args);
    }

}
