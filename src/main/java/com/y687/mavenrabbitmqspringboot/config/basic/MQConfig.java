package com.y687.mavenrabbitmqspringboot.config.basic;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2019/10/25 14:41
 * @Version
 */
@Configuration
public class MQConfig {

        @Bean
        public Queue queueOne(){
            return new Queue("postbar-test-three",true,false,false);
        }

        @Bean
        public Queue queueTwo(){
            return new Queue("postbar-test-fourth",true,false,false);
        }

        @Bean
        public Exchange exchangeDirect(){
            return new DirectExchange("postbar-test-direct-exchange",true,false);
        }

        @Bean
        public Binding bindQueueToExchange(){
            return BindingBuilder.bind(queueOne()).to(exchangeDirect()).with("aa").noargs();
        }

        @Bean
        public Binding bindQueueToExchange1(){
            return BindingBuilder.bind(queueTwo()).to(exchangeDirect()).with("ab").noargs();
        }

        @Bean
        public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
            return new RepublishMessageRecoverer(rabbitTemplate,"postbar-test-direct-exchange","ab");
        }
}
