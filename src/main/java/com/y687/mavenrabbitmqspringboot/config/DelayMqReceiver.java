package com.y687.mavenrabbitmqspringboot.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 延时消息接收
 *
 * @Author bin.yin
 * @createTime 2020/4/13 16:57
 * @Version
 */
@Slf4j
@Component
public class DelayMqReceiver {
    /**
     * 延时消息接收
     * @param msg
     * @param channel
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = {"com.wenwo.admin.manager.appointment.delayQueue"})
    public void receiver(String msg, Channel channel, Message message) throws Exception {
        log.info("message is:{}",msg);
        //log.info("message id:{}",message.getMessageProperties().getDeliveryTag());
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        throw  new Exception("消息异常");
    }
}
