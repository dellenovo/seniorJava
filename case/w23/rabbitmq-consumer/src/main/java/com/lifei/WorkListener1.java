package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "work_queue")
public class WorkListener1 {
    @RabbitHandler
    public void workHandler(String msg) {
        System.out.println("======工作队列接收消息端1=====> " + msg);
    }

}
