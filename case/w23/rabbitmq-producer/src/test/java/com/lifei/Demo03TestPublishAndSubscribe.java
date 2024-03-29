package com.lifei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03TestPublishAndSubscribe {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendPubSubMsg() {
        for (int i = 0; i < 100; i++)
            rabbitTemplate.convertAndSend("fanout_exchange", "", "hello " + i);
    }
}
