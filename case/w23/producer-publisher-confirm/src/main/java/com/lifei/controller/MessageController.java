package com.lifei.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/direct/sendMsg")
    public String sendMsg(String exchange, String routingkey, String msg) {
        rabbitTemplate.convertAndSend(exchange, routingkey, msg);
        return "已投递~";
    }
}
