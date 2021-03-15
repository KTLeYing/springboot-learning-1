/*package com.mzl.rabbitmqdemo5.mq.listener;

import com.mzl.rabbitmqdemo5.config.RabbitConfig;
import com.mzl.rabbitmqdemo5.mq.BaseConsumer;
import com.mzl.rabbitmqdemo5.mq.BaseConsumerProxy;
import com.mzl.rabbitmqdemo5.mq.consumer.SimpleMailConsumer;
import com.mzl.rabbitmqdemo5.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName :   MailListener
 * @Description: 使用动态代理方式进行消费；好处：当有多个消费监听队列时，可以使用动态代理方式处理公共部分代理逻辑
 * @Author: mzl
 * @CreateDate: 2020/10/23 16:28
 * @Version: 1.0
 */
/*@Component
@Slf4j
public class MailListener {

    @Autowired
    private SimpleMailConsumer mailConsumer;

    @Autowired
    private MsgLogService msgLogService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(mailConsumer, msgLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (proxy != null){
            proxy.consume(message, channel);
        }
    }
}
*/