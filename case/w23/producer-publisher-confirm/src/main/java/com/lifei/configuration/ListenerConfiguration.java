package com.lifei.configuration;

import com.lifei.listener.CustomAckConsumerListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfiguration {
    @Bean
    public MessageListenerAdapter messageListenerAdapter(CustomAckConsumerListener customAckConsumerListener){
        return new MessageListenerAdapter(customAckConsumerListener);
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                         MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setQueueNames("order.A");
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListenerAdapter);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return container;
    }
}
