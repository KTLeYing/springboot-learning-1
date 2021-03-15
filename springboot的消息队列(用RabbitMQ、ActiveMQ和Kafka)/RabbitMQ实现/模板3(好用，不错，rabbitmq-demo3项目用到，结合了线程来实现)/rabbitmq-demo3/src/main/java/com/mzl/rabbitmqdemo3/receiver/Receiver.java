package com.mzl.rabbitmqdemo3.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   Receiver
 * @Description: 消费者（监听接收生产者发送的消息）
 * @Author: mzl
 * @CreateDate: 2020/10/20 21:59
 * @Version: 1.0
 */
@Component
public class Receiver {

    /**
     * 消费者监听队列1
     * @param msg
     * @return
     */
    @RabbitListener(queues = {"queue1"})
    public String processMessage1(String msg){
        System.out.println(Thread.currentThread().getName() + " 接收到来自queue1队列的消息： " + msg);
        return msg.toLowerCase();
    }

    /**
     * 消费者监听队列2
     * @param msg
     * @return
     */
    @RabbitListener(queues = {"queue2"})
    public String processMessage2(String msg){
        System.out.println(Thread.currentThread().getName() + " 接收到来自queue2队列的消息：" + msg);
        return msg.toLowerCase();
    }
}
