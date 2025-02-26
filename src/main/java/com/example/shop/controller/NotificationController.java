package com.example.shop.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    // server application
    // url: /app/sendMessage
    @MessageMapping("/sendMessage")
    @SendTo("/topic/notification")
    public String sendMessage(String message){
        System.out.println("Message: " + message);
        return message;
    }
}
