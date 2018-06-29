package com.noeltest.oauthserver;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @GetMapping("/user/me")
    public Principal getUser(Principal user) {
        return user;
    }
}
