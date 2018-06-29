package com.noeltest.oauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
public class RunMain {    
    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }
}
