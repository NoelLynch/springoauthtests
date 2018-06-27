package com.noeltest.client;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("test")
    public SMessage test() {
        return new SMessage("i am a test");
    }
    
    
    // https://github.com/spring-projects/spring-boot/issues/5096
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("testREAD")
    public SMessage testREAD() {
        return new SMessage("i am a test READ");
    }
    
    // https://github.com/spring-projects/spring-boot/issues/5096
    @PreAuthorize("#oauth2.hasScope('write')")
    @GetMapping("testWRITE")
    public SMessage testWRITE() {
        return new SMessage("i am a test WRITE");
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("testREAD_auth")
    public SMessage testREAD_auth() {
        return new SMessage("i am a test READ with auth");
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("testADMIN_auth")
    public SMessage testADMIN_auth() {
        return new SMessage("i am a test ADMIN with auth");
    }
    
    @GetMapping("getUser")
    public Principal getUser(Principal user) {
        return user;
    }
    
    public static class SMessage {
        private String message;

        public SMessage(String message) {
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
