package com.noeltest.resourceserver1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("resourceserver1")
public interface ResourseServer1Client {
    @RequestMapping(value="/res1/message", method=RequestMethod.GET)
    ResMessage getMessage();
}
