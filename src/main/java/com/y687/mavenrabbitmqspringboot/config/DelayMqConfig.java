package com.y687.mavenrabbitmqspringboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2020/4/13 16:54
 * @Version
 */
@Configuration
public class DelayMqConfig {


    /**
     * 定义一个普通队列
     * @Author bin.yin
     * @createTime 2019/8/16 14:22
     * @param
     * @return: org.springframework.amqp.core.Queue
     * @throws
     * @version v1.5.2
     */
    @Bean
    public Queue planStatusQueue(){
        return new Queue("com.wenwo.admin.manager.appointment.delayQueue",true,false,false);
    }

    /**
     * 定义一个自定义交换机，交换机的第一类型为x-delayed-message
     * 第二类型为direct类型
     *
     *
     * 这里一定需要先在MQ安装rabbitmq-delayed-message-exchange插件
     * 否则，队列会无效
     *
     * @Author bin.yin
     * @createTime 2019/8/16 14:23
     * @param
     * @return: org.springframework.amqp.core.CustomExchange
     * @throws
     * @version v1.5.2
     */
    @Bean
    public CustomExchange planStatusExchange(){
        Map<String,Object> arguments =  new HashMap<>();
        arguments.put("x-delayed-type","direct");
        return new CustomExchange("com.wenwo.admin.manager.delayExchange","x-delayed-message",true,false,arguments);
    }
    /**
     * 将交换机和队列绑定
     * @Author bin.yin
     * @createTime 2019/8/16 14:23
     * @param
     * @return: org.springframework.amqp.core.Binding
     * @throws
     * @version v1.5.2
     */
    @Bean
    public Binding statusBind() {
        return BindingBuilder.bind(planStatusQueue()).to(planStatusExchange()).with("delay").noargs();
    }
}
