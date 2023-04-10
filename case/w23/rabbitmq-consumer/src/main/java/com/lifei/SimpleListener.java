package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "simple_queue")
public class SimpleListener {

    @RabbitHandler
    public void simpleHandler(String msg) {
        System.out.println("===receving messages====>" + msg);
    }
}
