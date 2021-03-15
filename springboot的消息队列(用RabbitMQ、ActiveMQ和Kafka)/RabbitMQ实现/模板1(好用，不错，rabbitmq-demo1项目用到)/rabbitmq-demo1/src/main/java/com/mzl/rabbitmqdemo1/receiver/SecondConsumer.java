package com.mzl.rabbitmqdemo1.receiver;

import com.mzl.rabbitmqdemo1.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者2
 * @author zhuzhe
 * @date 2018/5/25 17:32
 * @email 1529949535@qq.com
 */
@Component
public class SecondConsumer {

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_NAME1, RabbitMqConfig.QUEUE_NAME2})
    public void handleMessage(Message message) throws Exception {
        // 处理消息
        System.out.println("SecondConsumer {} handleMessage :"+message);
    }
}
