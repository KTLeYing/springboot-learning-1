package com.mzl.activemqdemo1.controller;

import com.mzl.activemqdemo1.consumer.FirstQueueConsumer;
import com.mzl.activemqdemo1.consumer.FirstTopicConsumer;
import com.mzl.activemqdemo1.producer.FirstQueueProducer;
import com.mzl.activemqdemo1.producer.FirstTopicProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

/**
 * @ClassName :   ProducerController
 * @Description: 生产者控制器
 * @Author: mzl
 * @CreateDate: 2020/11/24 17:54
 * @Version: 1.0
 */
@RestController
@Slf4j
public class ProducerController {

    @Autowired
    private FirstQueueProducer firstQueueProducer;
    @Autowired
    private FirstTopicProducer firstTopicProducer;

    /**
     * 生产者发送消息(队列)
     * @return
     */
    @RequestMapping("queueProduct")
    public String queueProduct() throws JMSException {
        return firstQueueProducer.product();
    }

    /**
     * 生产者发送消息(主题)
     * @return
     */
    @RequestMapping("topicProduct")
    public String topicProduct() throws JMSException {
        return firstTopicProducer.product();
    }


}
