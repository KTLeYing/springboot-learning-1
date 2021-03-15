package com.mzl.activemqdemo1.controller;

import com.mzl.activemqdemo1.consumer.FirstQueueConsumer;
import com.mzl.activemqdemo1.consumer.FirstTopicConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.io.IOException;

/**
 * @ClassName :   ConsumerController
 * @Description: 消费者控制器
 * @Author: mzl
 * @CreateDate: 2020/11/24 17:56
 * @Version: 1.0
 */
@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private FirstQueueConsumer firstQueueConsumer;

    @Autowired
    private FirstTopicConsumer firstTopicConsumer;

    /**
     * 消费者接收消息（队列）
     * @return
     * @throws IOException
     * @throws JMSException
     */
    @RequestMapping("queueConsume")
    public String queueConsume() throws IOException, JMSException {
        System.out.println("queueConsume......");
        return firstQueueConsumer.consume();
    }

    /**
     * 消费者接收消息（主题）
     * @return
     * @throws IOException
     * @throws JMSException
     */
    @RequestMapping("topicConsume")
    public String topicConsume() throws IOException, JMSException {
        System.out.println("topicConsume......");
        return firstTopicConsumer.consume();
    }


}
