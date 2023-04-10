package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "routing_queue1")
public class RoutingListenerInfo {
    @RabbitHandler
    public void handleRoutingMsg(String msg) {
        System.out.println(this.getClass().getSimpleName() + " get " + msg);
    }
}
