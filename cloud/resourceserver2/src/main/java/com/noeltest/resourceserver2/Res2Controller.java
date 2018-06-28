package com.noeltest.resourceserver2;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("res2")
public class Res2Controller {

    @GetMapping("message")
    public ResMessage getMessage() {
        return new ResMessage("i am resource server 2");
    }
    
    @GetMapping("user")
    public ResMessage getUser(Principal user) {
        return new ResMessage("i am " + user.getName());
    }
}
