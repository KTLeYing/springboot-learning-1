package com.mzl.rabbitmqdemo5.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @InterfaceName :   BaseConsumer
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/23 12:19
 * @Version: 1.0
 */
public interface BaseConsumer {

    void consume(Message message, Channel channel) throws IOException;
}
