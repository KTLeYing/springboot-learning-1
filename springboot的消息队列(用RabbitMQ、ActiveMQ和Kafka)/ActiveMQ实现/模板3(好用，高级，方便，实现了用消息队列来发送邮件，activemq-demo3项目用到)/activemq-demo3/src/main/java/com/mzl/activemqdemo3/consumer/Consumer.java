package com.mzl.activemqdemo3.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.activemqdemo3.pojo.Mail;
import com.mzl.activemqdemo3.service.MailService;
import com.mzl.activemqdemo3.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @ClassName :   Consumer
 * @Description: 消费者接收消息
 * @Author: mzl
 * @CreateDate: 2020/11/24 22:14
 * @Version: 1.0
 */
@Component
public class Consumer {

    @Autowired
    private MailService mailService;

    /**
     * 消费者接受信息，接受邮箱的相关内容，然后再用邮箱地址发送要发送的内容(对应消息参数是JsonObject)
     * @param msg
     * @return
     */
    //该注解自动监听目标队列里面的消息，一发现有消息就消费接收,注解里destination属性可以直接使用@Value的功能获取配置文件的属性值
    @JmsListener(destination = "${myqueue}")
    public void receive(String msg){
        if (StringUtils.isEmpty(msg)){
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + msg);

        //解析json字符串,转换为实体类对象
        Mail mail = JSON.parseObject(msg, Mail.class);
        mailService.sendCommonEmail(mail);   //发送普通文件
    }


    /**
     * 消费者接受信息，接受邮箱的相关内容，然后再用邮箱地址发送要发送的内容(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    //该注解自动监听目标队列里面的消息，一发现有消息就消费接收,注解里destination属性可以直接使用@Value的功能获取配置文件的属性值
//    @JmsListener(destination = "${myqueue}")
//    public void receive(MapMessage mapMessage) throws JMSException {
//        if (StringUtils.isEmpty(mapMessage)){   //队列中没有可监听的消息
//            return;
//        }
//
//        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());
//
//        //解析json字符串,转换为实体类对象
//        Mail mail = JSON.parseObject(mapMessage.toString(), Mail.class);
//        mailService.sendCommonEmail(mail);   //发送普通文件
//    }

    /**
     * 消费者接受信息，接受邮箱的相关内容，然后再用邮箱地址发送要发送的内容(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    @JmsListener(destination = "my_queue1")
    public void receive1(MapMessage mapMessage) throws JMSException {
        if (StringUtils.isEmpty(mapMessage)){  //队列中没有可监听的消息
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());

        //解析json字符串,转换为实体类对象
        Mail mail = JSON.parseObject(mapMessage.toString(), Mail.class);
        mailService.sendCommonEmail(mail);   //发送普通文件

    }

    /**
     * 消费者接受信息，接受邮箱的相关内容，然后再用邮箱地址发送要发送的内容(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    @JmsListener(destination = "my_queue2")
    public void receive2(MapMessage mapMessage) throws JMSException {
        if (StringUtils.isEmpty(mapMessage)){   //队列中没有可监听的消息
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());

        //解析json字符串,转换为实体类对象
        Mail mail = JSON.parseObject(mapMessage.toString(), Mail.class);
        mailService.sendCommonEmail(mail);   //发送普通文件
    }


}
