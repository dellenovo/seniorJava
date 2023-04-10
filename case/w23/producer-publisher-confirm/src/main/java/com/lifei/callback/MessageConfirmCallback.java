package com.lifei.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack) {
            System.out.println("消息进入交换机成功。");
        } else {
            System.out.println("消息进入交换机失败。失败原因：" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("交换机路由至消息队列出错：>>>>>>>");
        System.out.println("交换机："+exchange);
        System.out.println("路由键："+routingKey);
        System.out.println("错误状态码："+replyCode);
        System.out.println("错误原因："+replyText);
        System.out.println("发送消息内容："+message.toString());
        System.out.println("<<<<<<<<");
    }
}
