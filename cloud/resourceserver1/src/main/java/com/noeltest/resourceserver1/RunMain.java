package com.noeltest.resourceserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RunMain {

    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }

}
