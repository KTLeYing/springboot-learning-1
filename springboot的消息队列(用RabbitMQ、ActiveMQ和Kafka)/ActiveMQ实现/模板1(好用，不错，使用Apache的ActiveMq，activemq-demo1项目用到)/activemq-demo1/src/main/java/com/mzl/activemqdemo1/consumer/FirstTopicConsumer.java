package com.mzl.activemqdemo1.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.IOException;

/**
 * @ClassName : FirstTopicConsumer
 * @Description: 消费者（主题），在发布订阅消息传递域中，目的地被称为主题(topic)
 * 生产者将消息发布到topic中， 每个消息可以有多个消费者，属于1: N的关系
 * @Author: mzl
 * @CreateDate: 2020/11/24 20:04
 * @Version: 1.0
 */
@Slf4j
@Component
public class FirstTopicConsumer {

    @Value("${spring.activemq.url}")
    private String url;
    @Value("${spring.activemq.topic.name1}")
    private String topicName1;

    /**
     * 消费者接收消息（主题）
     * @return
     */
    public String consume() throws JMSException, IOException {
        //1.创建连接工厂，按照给定的URL地址，使用默认的用户和密码
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.通过连接工厂获取connection并访问
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //3.创建会话session, 两个参数，第一参数：事务，第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地，具体是队列Queue还是主题topic
        Topic topic1 = session.createTopic(topicName1);
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(topic1);

        //消费方式1：同步阻塞方式（receive）订阅者或接收者调用MessageConsumer的receive方法来接收，receive方法在接收到消息之前或超时之前将一直阻塞
//        while (true){
//            Message message = consumer.receive(6000);
//            if (message instanceof TextMessage){   //如果发过来的消息是TextMessage的一个实例化来的
//                TextMessage textMessage = (TextMessage) message;
//                System.out.println(textMessage);
//                System.out.println(textMessage.getText());
//            }else {
//                break;
//            }
//        }

        //消费方式2：异步非阻塞方式（监听器onMessage()方法）订阅者或接收者通过MessageConsumer的setMessageListener注册监听器，当消息到达之后，系统自动调用监听器MessageListener的onMessage方法
        consumer.setMessageListener(message ->  {
            if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try{
                    String text = textMessage.getText();   //获取textMessage里面的内容
                    System.out.println(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //缓存
//        System.in.read();

        //关闭资源(和创建的步骤刚好相反，队列的原理)
        consumer.close();
        session.close();
        connection.close();

        System.out.println("接收消息成功（主题）***********");
        return "接收消息成功（主题）***********";
    }

}
