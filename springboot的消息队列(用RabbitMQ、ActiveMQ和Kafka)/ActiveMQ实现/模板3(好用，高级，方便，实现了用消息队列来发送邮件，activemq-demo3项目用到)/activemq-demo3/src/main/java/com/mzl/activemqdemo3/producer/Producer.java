package com.mzl.activemqdemo3.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.activemqdemo3.pojo.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @ClassName :   Producer
 * @Description: 生产者发送消息
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
     * 方法1：生产者发送消息到队列，发送邮箱的相关内容（使用JmsMessagingTemplate）
     * @param
     */
//    @Scheduled(fixedDelay = 5000 * 60)
    public String send(Mail mail){
        String msg = JSONObject.toJSONString(mail);  //实体类对象转换为json字符串

        //直接使用convertAndSend发送到目标队列（使用Queue类型的queue作为队列参数，我们在queueConfig注册注入自定义的队列）
        jmsMessagingTemplate.convertAndSend(queue, msg);
        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + msg);
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + msg;
    }

    /**
     * 方法2：生产者发送消息到队列，发送邮箱的相关内容（使用JmsTemplate + 里面的convertAndSend（）方法）
     * @param
     * @return
     */
    public String send1(Mail mail){
        String msg = JSONObject.toJSONString(mail);  //实体类对象转换为json字符串

        // 直接使用convertAndSend发送到目标队列，（使用Queue类型的queue作为队列参数，我们在queueConfig注册注入自定义的队列）
        jmsTemplate.convertAndSend(queue, msg);

        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + msg);
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + msg;
    }

    /**
     * 方法3：生产者发送消息到队列，发送邮箱的相关内容（使用JmsTemplate + 里面的send()方法）
     * @param
     * @return
     */
    public String send2(Mail mail){
        // 法1：直接使用send发送到指定目标队列queue,消息参数只能使用自带的MapMessage（使用Queue类型的queue作为队列参数，我们
        // 在queueConfig注册注入自定义的队列）
//        jmsTemplate.send(queue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setObject("tos", mail.getTos());
//                mapMessage.setObject("content", mail.getContent());
//                mapMessage.setObject("subject", mail.getSubject());
//                return mapMessage;
//            }
//        });

        //法2：直接使用send发送到指定目标队列名，消息参数只能使用自带的MapMessage,(使用String类型queue的名字作为队列参数，不用我们
        //在queueConfig注册注入自定义的队列了，jmsTemplate自动帮我们在send()方法里创建目标队列,并发送消息到该队列)
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setObject("tos", mail.getTos());
                mapMessage.setObject("content", mail.getContent());
                mapMessage.setObject("subject", mail.getSubject());
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
//                mapMessage.setObject("tos", mail.getTos());
//                mapMessage.setObject("content", mail.getContent());
//                mapMessage.setObject("subject", mail.getSubject());
//                return mapMessage;
//            }
//        });

        System.out.println("采用点对点通讯模式，生产者发送消息成功,msg: " + mail.toString());
        return "采用点对点通讯模式，生产者发送消息成功,msg: " + mail.toString();
    }


}
