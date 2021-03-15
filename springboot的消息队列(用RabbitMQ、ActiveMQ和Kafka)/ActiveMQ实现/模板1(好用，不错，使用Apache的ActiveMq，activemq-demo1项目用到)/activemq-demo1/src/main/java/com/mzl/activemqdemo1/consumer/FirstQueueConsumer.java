package com.mzl.activemqdemo1.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.IOException;

/**
 * @ClassName : FirstConsumer
 * @Description: 消费者(队列)， 在点对点的消息传递域中，目的地被称为队列(Queue)
 * 每个消息只能有一个消费者，类似1对1的关系。好比个人快递自己领取自己的。
 * @Author: mzl
 * @CreateDate: 2020/11/24 17:57
 * @Version: 1.0
 */
@Slf4j
@Component
public class FirstQueueConsumer {

    @Value("${spring.activemq.url}")
    private String url;
    @Value("${spring.activemq.queue.name1}")
    private String queueName1;

    /**
     * 消费者接收消息（队列）
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
        Queue queue1 = session.createQueue(queueName1);
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(queue1);

    // 消费方式1：同步阻塞方式（receive）订阅者或接收者调用MessageConsumer的receive方法来接收，receive方法在接收到消息之前或超时之前将一直阻塞
            while (true){
                Message message = consumer.receive(6000);
                if (message instanceof TextMessage){   //如果发过来的消息是TextMessage的一个实例化来的
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println(textMessage);
                    System.out.println(textMessage.getText());
                }else {
                    break;
                }
            }

    // 消费方式2：异步非阻塞方式（监听器onMessage()方法）订阅者或接收者通过MessageConsumer的setMessageListener注册监听器，当消息到达之后，系统自动调用监听器MessageListener的onMessage方法
//    consumer.setMessageListener(
//        new MessageListener() {
//          @Override
//          public void onMessage(Message message) {
//            if (message instanceof TextMessage) {
//              TextMessage textMessage = (TextMessage) message;
//              try {
//                String text = textMessage.getText(); // 获取textMessage里面的内容
//                System.out.println(text);
//              } catch (Exception e) {
//                e.printStackTrace();
//              }
//            }
//          }
//        });

        //缓存
//        System.in.read();

        //关闭资源(和创建的步骤刚好相反，队列的原理)
        consumer.close();
        session.close();
        connection.close();

        System.out.println("接收消息成功（队列）***********");
        return "接收消息成功（队列）***********";
    }


}
