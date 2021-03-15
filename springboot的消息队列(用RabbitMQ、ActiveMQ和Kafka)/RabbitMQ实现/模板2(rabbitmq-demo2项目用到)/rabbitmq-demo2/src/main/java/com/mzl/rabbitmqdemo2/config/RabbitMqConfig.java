package com.mzl.rabbitmqdemo2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   RabbitMqConfig
 * @Description: RabbitMq配置类
 * @Author: mzl
 * @CreateDate: 2020/10/20 19:38
 * @Version: 1.0
 */
@Configuration
public class RabbitMqConfig {

    //创建两个消息队列
    public static final String DEFAULT_BOOK_QUEUE = "book.default.queue";
    public static final String MANUAL_BOOK_QUEUE = "book.manual.queue";

    /**
     * 配置队列defaultQueue
     * @return
     */
    @Bean
    public Queue defaultBookQueue(){
        //第一个是队列的名称，第二个是消息是否需要持久化处理
        return new Queue(DEFAULT_BOOK_QUEUE, true);
    }

    /**
     * 配置队列manualQueue
     * @return
     */
    @Bean
    public Queue manualBookQueue(){
        return new Queue(MANUAL_BOOK_QUEUE, true);
    }


}
