package com.noeltest.resourceserver1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("res1")
public class ResMessageController {
    @GetMapping("message")
    public ResMessage getMessage() {
        return new ResMessage("I am resource server 1");
    }
    
    public static class ResMessage {
        private String msg;

        public ResMessage(String msg) {
            super();
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
