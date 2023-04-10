package com.lifei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02TestWorkQueue {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testWorkQueue() {
        for (int i = 0; i < 1000; i++) {
            rabbitTemplate.convertAndSend("work_queue", "Hello, I am Lifei " + i);
        }
    }
}
