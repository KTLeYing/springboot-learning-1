package com.mzl.activemqdemo2.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;


/**
 * @ClassName :   QueueConfig
 * @Description: 队列配置类
 * @Author: mzl
 * @CreateDate: 2020/11/24 22:07
 * @Version: 1.0
 */
@Configuration
public class QueueConfig {

    @Value("${myqueue}")
    private String myQueue;

    /**
     * 将自定义的myqueue队列注入/注册到spring容器中自动起作用，后面使用到的javax.jms.Queue都是自定义的名为myQueue的队列来的
     * @return
     */
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }


}
