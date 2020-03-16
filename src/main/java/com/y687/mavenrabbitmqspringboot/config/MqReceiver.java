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

    //此注解可以设置消费者的并发数量、并发使用的执行器等等属性~~
    @RabbitListener(queues = "postbar-test-three",concurrency = "10")
    public void mqReceiver(String msg, Channel channel, Message message) throws Exception {
        log.info("message is:{}",msg);
        //log.info("message id:{}",message.getMessageProperties().getDeliveryTag());
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        throw  new Exception("消息异常");

    }
}
