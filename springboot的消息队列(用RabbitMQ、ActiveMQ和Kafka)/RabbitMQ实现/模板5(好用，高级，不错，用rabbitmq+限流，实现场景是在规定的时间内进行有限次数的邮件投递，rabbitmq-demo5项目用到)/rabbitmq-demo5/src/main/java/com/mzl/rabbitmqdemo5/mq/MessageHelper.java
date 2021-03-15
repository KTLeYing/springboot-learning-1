package com.mzl.rabbitmqdemo5.mq;

import com.mzl.rabbitmqdemo5.utils.JsonUtils;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

/**
 * @ClassName :   MessageHelper
 * @Description: 消息辅助类
 * @Author: mzl
 * @CreateDate: 2020/10/23 15:14
 * @Version: 1.0
 */
public class MessageHelper {

    public static Message objToMsg(Object obj){
        if (obj == null){
            return null;
        }

        Message message = MessageBuilder.withBody(JsonUtils.objToStr(obj).getBytes()).build();
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);     //消息持久化
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);

        return message;
    }

    /**
     * 把消息字符串转换为消息对象
     * @param message
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T msgToObj(Message message, Class<T> clazz){
        if (null == message || null == clazz){
            return null;
        }

        String str = new String(message.getBody());
        T obj = JsonUtils.strToObj(str, clazz);

        return obj;
    }


}
