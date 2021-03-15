package com.mzl.ticketgrabbing.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   MQConfig
 * @Description: 消息队列配置类
 * @Author: mzl
 * @CreateDate: 2021/1/11 23:03
 * @Version: 1.0
 */
@Configuration
public class MQConfig {

    /**
     * 创建一个消息队列（在编译加载的时候就注册尽量容器作为原料并起作用，是真正的有用的队列来的）
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MQConstant.QUEUE, true);
    }

}
