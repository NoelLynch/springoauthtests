package com.noeltest.springoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
public class RunMain extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }

}
