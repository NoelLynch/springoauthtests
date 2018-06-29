package com.noeltest.oauthserver;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
public class RunMain {

    @Bean
    public ApplicationRunner initUsers(MongoUserService userService) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                userService.deleteAll();
                
                userService.createUser("john", "123", Arrays.asList("ADMIN", "USER"));
                userService.createUser("tom", "123", Arrays.asList("USER"));
            }
        };
    }
    
    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }

}
