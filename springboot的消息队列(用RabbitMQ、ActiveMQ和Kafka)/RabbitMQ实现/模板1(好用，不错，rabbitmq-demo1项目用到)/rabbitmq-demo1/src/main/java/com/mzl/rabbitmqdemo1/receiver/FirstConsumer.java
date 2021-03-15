package com.mzl.rabbitmqdemo1.receiver;

import com.mzl.rabbitmqdemo1.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者1
 * @author zhuzhe
 * @date 2018/5/25 17:32
 * @email 1529949535@qq.com
 */
@Component
public class FirstConsumer {

    /**
     * queues  指定从哪个队列（queue）订阅消息
     * @param message
     */
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_NAME1, RabbitMqConfig.QUEUE_NAME2})
    public void handleMessage(Message message){
        // 处理消息
        System.out.println("FirstConsumer {} handleMessage :"+message);
    }
}
