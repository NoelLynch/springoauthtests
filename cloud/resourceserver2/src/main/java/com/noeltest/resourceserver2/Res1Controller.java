package com.noeltest.resourceserver2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("res1")
public class Res1Controller {

    @Autowired private ResourseServer1Client res1Client;
    
    @GetMapping("res1message")
    public ResMessage getRes1Message() {
        return res1Client.getMessage();
    }
    
    @GetMapping("inspectHeader")
    public void inspectHeader(@RequestHeader("Authorization") String auth) {
        System.out.println("auth is => " + auth);
    }
    
    @GetMapping("res1messageWithHeader")
    public ResMessage getRes1MessageWithHeader(@RequestHeader("Authorization") String auth) {
        return res1Client.getMessageWithHeader(auth);
    }
}
