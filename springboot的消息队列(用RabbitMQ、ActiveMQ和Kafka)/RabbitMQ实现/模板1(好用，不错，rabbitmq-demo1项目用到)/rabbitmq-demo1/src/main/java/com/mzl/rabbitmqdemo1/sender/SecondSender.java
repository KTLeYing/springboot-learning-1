package com.mzl.rabbitmqdemo1.sender;

import com.mzl.rabbitmqdemo1.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   SecondConsumer
 * @Description: 发送消息(生产者2)
 * @Author: mzl
 * @CreateDate: 2020/10/19 19:01
 * @Version: 1.0
 */
@Component
public class SecondSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息（生产者2）
     * @param uuid
     * @param message
     */
    public void send(String uuid, Object message){
        //会发送消息后自动产生一个相关消息的id
        CorrelationData correlationId = new CorrelationData(uuid);
        //RabbitMqConfig.EXCHANGE是交换机，RabbitMqConfig.ROUTINGKEY2是指定的消息队列2
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY2, message, correlationId);
    }



}
