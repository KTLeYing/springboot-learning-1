package com.mzl.rabbitmqdemo1.sender;

import com.mzl.rabbitmqdemo1.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息发送  生产者1
 * @author zhuzhe
 * @date 2018/5/25 14:28
 * @email 1529949535@qq.com
 */
@Slf4j
@Component
public class FirstSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息（生产者1）
     * @param uuid
     * @param message  消息
     */
    public void send(String uuid,Object message) {
        CorrelationData correlationId = new CorrelationData(uuid);
        /**
         * RabbitMqConfig.EXCHANGE  指定消息交换机
         * RabbitMqConfig.ROUTINGKEY1  指定队列key1
         */
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY1, message, correlationId);
    }

}
