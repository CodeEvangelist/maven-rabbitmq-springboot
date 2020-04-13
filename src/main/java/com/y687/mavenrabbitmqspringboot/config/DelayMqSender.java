package com.y687.mavenrabbitmqspringboot.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2020/4/13 16:54
 * @Version
 */
@Component
@Slf4j
public class DelayMqSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    /**
     * mq模板
     */
    @Resource
    private AmqpTemplate amqpTemplate;


    /***
     * 发送延时mq消息
     * @Author bin.yin
     * @createTime 2019/8/16 10:11
     * @param msg  消息
     * @param ttl  延迟时间，单位为毫秒
     * @throws
     * @version v1.5.2
     */
    public void send(String msg, Long ttl){
        log.info("appointmentStatusMessage send vo:{}", JSON.toJSONString(msg));
        amqpTemplate.convertAndSend("com.wenwo.admin.manager.delayExchange",
                "delay",
                msg,
                message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setHeader("x-delay",ttl);
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );

    }

    /**
     * 如果消息到达交换机，而是未到达队列，则回调此方法
     * 模拟方式:指定没有的routingKey，这样exchange消息就会丢失了，此方法就会被回调
     *
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //log.info("message:{} send result is replyCode:{}--replyTest:{} exchange:{} routingkey:{}",
        //JSON.toJSONString(message),replyCode,replyText,exchange,routingKey);
    }
    /**
     * 不论是否消息到达否，都会回调此方法
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(!ack){
            log.error("消息发送失败 原因:{}  {}", JSON.toJSONString(correlationData),cause);
        }else{
            log.info("消息发送成功");
        }
    }
}
