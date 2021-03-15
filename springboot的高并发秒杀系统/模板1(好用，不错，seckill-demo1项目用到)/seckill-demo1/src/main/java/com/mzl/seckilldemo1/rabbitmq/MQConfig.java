package com.mzl.seckilldemo1.rabbitmq;

import org.apache.ibatis.annotations.Insert;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   MQConfig
 * @Description: 消息队列配置类
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:10
 * @Version: 1.0
 */
@Configuration
public class MQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchage";

    /**
     * Direct模式 交换机Exchange，主秒杀队列
     * 发送者先发送到交换机上，然后交换机作为路由再将信息发到队列，
     * */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true);
    }

    /**
     * Topic模式
     * */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2, true);
    }

    /**
     * Topic模式 交换机Exchange
     * */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 将队列绑定到交换机上
     */
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.key2");
    }

}
