package com.mzl.rabbitmqdemo5.mq.consumer;

import com.mzl.rabbitmqdemo5.exception.ServiceException;
import com.mzl.rabbitmqdemo5.mq.BaseConsumer;
import com.mzl.rabbitmqdemo5.mq.BaseConsumerProxy;
import com.mzl.rabbitmqdemo5.mq.MessageHelper;
import com.mzl.rabbitmqdemo5.pojo.Mail;
import com.mzl.rabbitmqdemo5.utils.MailUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName :   MailConsumer
 * @Description: 邮件监听者，消费者
 * @Author: mzl
 * @CreateDate: 2020/10/23 15:35
 * @Version: 1.0
 */
@Component
@Slf4j
public class MailConsumer implements BaseConsumer {

    @Autowired
    private MailUtils mailUtils;

    /***
     * 消费者接收消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @Override
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);      //把消息字符串转换为消息对象
        log.info("收到消息: {}", mail.toString());   //mail对象转换为对象字符串

        //消息消费成功，发邮件给生产者，准备设置消息确认号
        boolean success = mailUtils.send(mail);
        if (!success){
            throw new ServiceException("send mail error");
        }
    }
}
