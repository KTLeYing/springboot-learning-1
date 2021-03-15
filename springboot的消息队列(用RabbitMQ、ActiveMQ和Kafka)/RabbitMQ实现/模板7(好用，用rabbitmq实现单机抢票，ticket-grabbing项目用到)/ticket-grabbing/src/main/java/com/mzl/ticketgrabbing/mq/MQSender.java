package com.mzl.ticketgrabbing.mq;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.api.scripting.JSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   MQSender
 * @Description: 消息队列发送者，发送消息到队列里
 * @Author: mzl
 * @CreateDate: 2021/1/11 21:28
 * @Version: 1.0
 */
@Service
@Slf4j
public class MQSender {

    //注入消息队列的模板依赖（里面有有关的实现消息各种处理的方法）
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(Message message){
        //发送消息只能是字符串，所以要把实体类对象转换为实体类字符串
        String msg = JSONObject.toJSONString(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConstant.QUEUE, msg);
    }

}
