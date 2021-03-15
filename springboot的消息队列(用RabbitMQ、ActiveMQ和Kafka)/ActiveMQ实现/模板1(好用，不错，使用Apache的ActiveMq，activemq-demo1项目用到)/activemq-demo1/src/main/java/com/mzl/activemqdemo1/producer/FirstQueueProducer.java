package com.mzl.activemqdemo1.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @ClassName : FirstProducer
 * @Description: 生产者（队列）， 在点对点的消息传递域中，目的地被称为队列(Queue) *
 * 每个消息只能有一个消费者，类似1对1的关系。好比个人快递自己领取自己的。
 * @Author: mzl
 * @CreateDate: 2020/11/24 17:57
 * @Version: 1.0
 */
@Slf4j
@Component
public class FirstQueueProducer {

    @Value("${spring.activemq.url}")
    private String url;
    @Value("${spring.activemq.queue.name1}")
    private String queueName1;

    /**
     * 生产者发送消息（队列）
     * @return
     * @throws JMSException
     */
    public String product() throws JMSException {
        //1.创建连接工厂，按照给定的URL地址，使用默认的用户和密码
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.通过连接工厂获取connection并访问
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //3.创建会话session,两个参数，第一参数：事务，第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地，具体是队列Queue还是主题topic
        Queue queue1 = session.createQueue(queueName1);
        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue1);
        // 6. 通过消息生产者生产消息到MQ
        for (int i = 0; i < 3; i++) {
            //6.1 创建消息
            //文本消息
            TextMessage textMessage = session.createTextMessage("Hello activeMQ-- queue msg " + i);   //把内容设置在TextMessage里面
            //6.2 发送到MQ
            producer.send(textMessage);
        }

        //7.关闭资源(先创建的后关闭，队列的原理)
        producer.close();
        session.close();
        connection.close();

        System.out.println("队列消息发送成功（队列）************");
        return "队列消息发送成功（队列）************";
    }

}
