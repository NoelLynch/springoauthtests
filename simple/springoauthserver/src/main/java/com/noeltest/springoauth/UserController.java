package com.noeltest.springoauth;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }
    
    @GetMapping("/user/hello")
    public RMessage hello() {
        return new RMessage("hello i am here!");
    }
    
    public static class RMessage {
        private String message;

        public RMessage(String message) {
            super();
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        
        
    }
}