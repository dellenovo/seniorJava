package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout_queue2")
public class PubAndSubListener2 {
    @RabbitHandler
    public void pubAndSubHandler(String msg) {
        System.out.println("=====> PubAndSubListener2 receives msg: " + msg);
    }
}
