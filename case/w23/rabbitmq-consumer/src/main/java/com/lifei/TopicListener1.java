package com.lifei;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic_queue1")
public class TopicListener1 {
    @RabbitHandler
    public void topicHandler(String msg) {
        System.out.println("=====> TopicListener1 receives msg: " + msg);
    }
}
