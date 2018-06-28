package com.noeltest.resourceserver1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
