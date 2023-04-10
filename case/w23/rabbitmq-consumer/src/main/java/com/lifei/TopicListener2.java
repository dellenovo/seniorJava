package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic_queue2")
public class TopicListener2 {
    @RabbitHandler
    public void topicHandler(String msg) {
        System.out.println("=====> TopicListener2 receives msg: " + msg);
    }
}
