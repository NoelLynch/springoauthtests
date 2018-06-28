package com.noeltest.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RunMain {

    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }

}
