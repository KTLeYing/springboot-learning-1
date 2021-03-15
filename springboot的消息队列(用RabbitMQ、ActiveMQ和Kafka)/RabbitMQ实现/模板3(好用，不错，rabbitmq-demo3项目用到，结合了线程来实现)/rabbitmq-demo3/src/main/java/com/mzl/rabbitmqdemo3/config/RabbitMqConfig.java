package com.mzl.rabbitmqdemo3.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   RabbitMqConfig
 * @Description: RabbitMq配置
 * @Author: mzl
 * @CreateDate: 2020/10/20 21:05
 * @Version: 1.0
 */
@Configuration
public class RabbitMqConfig {

    //声明队列
    @Bean
    public Queue queue1(){
        return new Queue("queue1", true);
    }

    @Bean
    public Queue queue2(){
        return new Queue("queue2", true);
    }

    //声明交换机
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    //绑定队列到到交换机上(每个消息队列绑定到交换机上都要一个路由key，与网络的交换机类似)
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("routingKey1");
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("routingKey2");
    }


}
