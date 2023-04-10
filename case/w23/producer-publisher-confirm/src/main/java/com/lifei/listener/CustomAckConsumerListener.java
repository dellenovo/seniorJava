package com.lifei.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class CustomAckConsumerListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] messageBody = message.getBody();
        String msg = new String(messageBody, "utf-8");
        System.out.println("接收到消息，执行具体业务逻辑{} 消息内容：" + msg);

        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            if (msg.contains("apple")) {
                throw new RuntimeException("No Apple!");
            }
            channel.basicAck(deliveryTag, false);

            System.out.println("手动签收完成：{}");
        } catch (Exception ex) {
            channel.basicNack(deliveryTag, false, false);

            System.out.println("拒绝签收，重回队列：{}" + ex);
        }
    }
}
