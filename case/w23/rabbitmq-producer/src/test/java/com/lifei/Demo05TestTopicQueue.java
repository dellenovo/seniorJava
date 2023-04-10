package com.lifei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo05TestTopicQueue {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRoutingMode() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("topic_exchange", i % 2 == 0 ? "item.insert" : "item.insert.abc",
                    (i % 2 == 0 ? "item.insert " : "item.insert.abc ") + i);
        }
    }
}
