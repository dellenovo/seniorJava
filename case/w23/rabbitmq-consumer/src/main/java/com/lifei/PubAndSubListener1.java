package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout_queue1")
public class PubAndSubListener1 {
    @RabbitHandler
    public void pubAndSubHandler(String msg) {
        System.out.println("=====> PubAndSubListener1 receives msg: " + msg);
    }
}
