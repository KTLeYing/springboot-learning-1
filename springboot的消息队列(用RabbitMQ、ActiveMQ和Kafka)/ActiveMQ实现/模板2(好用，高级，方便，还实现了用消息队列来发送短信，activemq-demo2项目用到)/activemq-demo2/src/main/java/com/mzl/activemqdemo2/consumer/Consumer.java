package com.mzl.activemqdemo2.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.activemqdemo2.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @ClassName :   Consumer
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/24 22:14
 * @Version: 1.0
 */
@Component
public class Consumer {

    @Autowired
    private SmsService smsService;

    /**
     * 消费者接受信息，接受手机号码，然后再用手机号码发送短信(对应消息参数是JsonObject)
     * @param msg
     * @return
     */
    //该注解自动监听目标队列里面的消息，一发现有消息就消费接收,注解里destination属性可以直接使用@Value的功能获取配置文件的属性值
    @JmsListener(destination = "${myqueue}")
    public void receive(String msg){
        if (StringUtils.isEmpty(msg)){     //队列中没有可监听的消息
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + msg);

        //解析json字符串,转换为json对象
        JSONObject jsonObject = JSON.parseObject(msg);
        String username = jsonObject.getString("username");
        String phone = jsonObject.getString("phone");
        smsService.sendSms(phone);  //发送短信

    }


    /**
     * 消费者接受信息，接受手机号码，然后再用手机号码发送短信(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    //该注解自动监听目标队列里面的消息，一发现有消息就消费接收,注解里destination属性可以直接使用@Value的功能获取配置文件的属性值
//    @JmsListener(destination = "${myqueue}")
//    public void receive(MapMessage mapMessage) throws JMSException {
//        if (StringUtils.isEmpty(mapMessage)){  //队列中没有可监听的消息
//            return;
//        }
//
//        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());
//
//        //解析消息map
//        String username = mapMessage.getString("username");
//        String phone = mapMessage.getString("phone");
//        smsService.sendSms(phone);   //发送短信
//
//    }

    /**
     * 消费者接受信息，接受手机号码，然后再用手机号码发送短信(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    @JmsListener(destination = "myqueue1")
    public void receive1(MapMessage mapMessage) throws JMSException {
        if (StringUtils.isEmpty(mapMessage)){  //队列中没有可监听的消息
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());

        //解析消息map
        String username = mapMessage.getString("username");
        String phone = mapMessage.getString("phone");
        smsService.sendSms(phone);   //发送短信
    }

    /**
     * 消费者接受信息，接受手机号码，然后再用手机号码发送短信(对应消息参数是自带的MapMessage)
     * @param mapMessage
     * @return
     */
    @JmsListener(destination = "myqueue2")
    public void receive2(MapMessage mapMessage) throws JMSException {
        if (StringUtils.isEmpty(mapMessage)){   //队列中没有可监听的消息
            return;
        }

        System.out.println("采用点对点模式，消费者成功获取到生产者的消息,msg:" + mapMessage.toString());

        //解析消息map
        String username = mapMessage.getString("username");
        String phone = mapMessage.getString("phone");
        smsService.sendSms(phone);   //发送短信

    }


}
