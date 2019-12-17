package com.y687.mavenrabbitmqspringboot.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2019/10/25 15:28
 * @Version
 */
@Component
@Slf4j
public class MqReceiver {

    @RabbitListener(queues = "postbar-test-three")
    public void mqReceiver(String msg, Channel channel, Message message) throws Exception {
        log.info("message is:{}",msg);
        //log.info("message id:{}",message.getMessageProperties().getDeliveryTag());
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        throw  new Exception("消息异常");

    }
}
