package com.mzl.activemqdemo2.producer;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @ClassName :   Producer
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/24 22:13
 * @Version: 1.0
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    //这里的queue具体已经是我自定义的队列来的了，因为我使用了队列配置类来将自定义的队列注入/注册到spring容器中,编译加载时自动起作用，后面使用到的javax.jms.Queue都是自定义的myQueue队列来的
    @Autowired
    private Queue queue;

    //一个队列
    @Value("${myqueue}")
    private String queueName;

    //多个队列
    @Value("${morequeue}")
    private String moreQueue;

    /**
     * 方法1：生产者发送消息到队列，发送手机号码（使用JmsMessagingTemplate）
     * @param phone
     */
//    @Scheduled(fixedDelay = 5000 * 60)
    public String send(String phone){
        JSONObject jsonObject = new JSONObject();   //json对象
        jsonObject.put("phone", phone);
        jsonObject.put("username", String.valueOf(System.currentTimeMillis()));  //每次发送的用户名是时间戳
        String msg = jsonObject.toJSONString();  //json对象转换为字符串

        //直接使用convertAndSend发送到目标队列（使用Queue类型的queue作为队列参数，我们在queueConfig注册注入自定义的队列）
        jmsMessagingTemplate.convertAndSend(queue, msg);
        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + msg);
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + msg;
    }

    /**
     * 方法2：生产者发送消息到队列，发送手机号码（使用JmsTemplate + 里面的convertAndSend（）方法）
     * @param phone
     * @return
     */
    public String send1(String phone){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("username", String.valueOf(System.currentTimeMillis()));
        String msg = jsonObject.toJSONString();

        // 直接使用convertAndSend发送到目标队列，（使用Queue类型的queue作为队列参数，我们在queueConfig注册注入自定义的队列）
        jmsTemplate.convertAndSend(queue, msg);

        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + msg);
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + msg;
    }

    /**
     * 方法3：生产者发送消息到队列，发送手机号码（使用JmsTemplate + 里面的send()方法）
     * @param phone
     * @return
     */
    public String send2(String phone){
        // 法1：直接使用send发送到指定目标队列queue,消息参数只能使用自带的MapMessage（使用Queue类型的queue作为队列参数，我们
        // 在queueConfig注册注入自定义的队列）
//        jmsTemplate.send(queue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setString("phone", phone);
//                mapMessage.setObject("username", String.valueOf(System.currentTimeMillis()));
//                return mapMessage;
//            }
//        });

        //法2：直接使用send发送到指定目标队列名，消息参数只能使用自带的MapMessage,(使用String类型queue的名字作为队列参数，不用我们
        //在queueConfig注册注入自定义的队列了，jmsTemplate自动帮我们在send()方法里创建目标队列,并发送消息到该队列)
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("phone", phone);
                mapMessage.setObject("username", String.valueOf(System.currentTimeMillis()));
                return mapMessage;
            }
        });

        //法3：直接使用send发送到指定目标队列名，消息参数只能使用自带的MapMessage，发送到多个队列(使用String类型queue的名字作为队列参数，不用我们
        // 在queueConfig注册注入自定义的队列了，jmsTemplate自动帮我们在send()方法里创建目标队列,并发送消息到该队列)
        //创建队列   queueName表示队列名称, 多个队列之间采用","分隔。如下： "my-queue1, my-queue2"
//        jmsTemplate.send(moreQueue,  new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setString("phone", phone);
//                mapMessage.setObject("username", String.valueOf(System.currentTimeMillis()));
//                return mapMessage;
//            }
//        });

        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + phone);
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + phone;
    }


}
