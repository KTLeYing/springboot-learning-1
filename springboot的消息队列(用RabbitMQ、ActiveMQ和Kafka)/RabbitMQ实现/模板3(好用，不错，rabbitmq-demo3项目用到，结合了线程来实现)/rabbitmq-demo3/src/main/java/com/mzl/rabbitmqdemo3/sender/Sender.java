package com.mzl.rabbitmqdemo3.sender;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @ClassName :   Sender
 * @Description: 生产者发送消息
 * @Author: mzl
 * @CreateDate: 2020/10/20 21:21
 * @Version: 1.0
 */
@Component
public class Sender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //this是当前对象，继承了RabbitTemplate的tConfirmCallback和ReturnCallback
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 消息确认方法
     * @param correlationData
     * @param
     * @param
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack){
            //如果收到了确认号
            System.out.println("消息发送成功:" + correlationData);
        }else {
            System.out.println("消息发送失败：" + correlationData);
        }
    }

    /**
     * 消息发送失败回调方法
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println(message.getMessageProperties().getCorrelationId() + " 发送失败");
    }

    /**
     * 发送消息，不需要实现任何接口，供外部调用，为每个消息产生一个唯一的id。
     * @param msg
     */
    public void send(String msg){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        System.out.println("队列1开始发送消息： " + msg.toLowerCase());
        String response1 = rabbitTemplate.convertSendAndReceive("topicExchange", "routingKey1", msg, correlationId).toString();
        System.out.println("发送消息结束： " + msg.toLowerCase());
        System.out.println("消费者1响应： " + response1 + " 消息处理完成");
        System.out.println();
        System.out.println("队列2开始发送消息： " + msg.toLowerCase());
        String response2 = rabbitTemplate.convertSendAndReceive("topicExchange", "routingKey2", msg, correlationId).toString();
        System.out.println("发送消息结束： " + msg.toLowerCase());
        System.out.println("消费者2响应： " + response2 + " 消息处理完成");
        System.out.println("#---------------------END---------------------#");
    }

}
