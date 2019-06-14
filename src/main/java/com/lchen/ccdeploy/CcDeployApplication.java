package com.lchen.ccdeploy;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableAdminServer
@EnableScheduling
@SpringBootApplication
public class CcDeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcDeployApplication.class, args);
    }

}
