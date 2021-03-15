package com.mzl.seckilldemo1.rabbitmq;

import com.mzl.seckilldemo1.redis.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   MQSender
 * @Description:  消息队列发送者（生产者）
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:12
 * @Version: 1.0
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
    }

    /**
     * 生产者，秒杀消息的生产者，消息体是SeckillMessage
     * @param seckillMessage
     */
    public void sendSeckillMessage(SeckillMessage seckillMessage){
        System.out.println("生产者生产...");
        String msg = RedisService.beanToString(seckillMessage);
        log.info("send message:" + msg );
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }


}
