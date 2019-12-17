package com.y687.mavenrabbitmqspringboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2019/10/25 15:00
 * @Version
 */
@Component
@Slf4j
public class MqSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    //发送消息到没有的routingKey，会调用returnedMessage
    public void send(String msg){
        //log.info("send msg:{}",msg);
        this.rabbitTemplate.convertAndSend("postbar-test-direct-exchange","ac",msg);
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
            //log.error("消息发送失败 原因:{}  {}", JSON.toJSONString(correlationData),cause);
        }else{
            //log.info("消息发送成功");
        }
    }
}
