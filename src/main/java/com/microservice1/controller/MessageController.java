package com.microservice1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
//    http://localhost:8080/api/v1/message/getMessage1
    @GetMapping("/getMessage1")
    public String getMessage1(){
        return "Welcome1";
    }
    @GetMapping("/getMessage2")
    public String getMessage2(){
        return "Welcome2";
    }
}
