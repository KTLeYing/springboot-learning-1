package com.mzl.rabbitmqdemo5.mq.consumer;

import com.mzl.rabbitmqdemo5.common.Constant;
import com.mzl.rabbitmqdemo5.config.RabbitConfig;
import com.mzl.rabbitmqdemo5.mq.MessageHelper;
import com.mzl.rabbitmqdemo5.pojo.Mail;
import com.mzl.rabbitmqdemo5.pojo.MsgLog;
import com.mzl.rabbitmqdemo5.service.MsgLogService;
import com.mzl.rabbitmqdemo5.utils.MailUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.management.MemoryUsage;

/**
 * @ClassName :   SimpleMailConsumer
 * @Description: 听接收发送的邮件，消费者
 * @Author: mzl
 * @CreateDate: 2020/10/23 16:07
 * @Version: 1.0
 */
@Component
@Slf4j
public class SimpleMailConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailUtils mailUtils;

    /***
     * 监听接收发送的邮件
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息: {}", mail.toString());

        String msgId = mail.getMsgId();
        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if (msgLog  == null || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)){ // 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }

        MessageProperties messageProperties = message.getMessageProperties();
        long tag = messageProperties.getDeliveryTag();

        //消息消费成功，发邮件给生产者，准备设置消息确认号
        boolean success = mailUtils.send(mail);
        if (success){
            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(tag, false);  //消费成功确认
        }else {
            channel.basicNack(tag, false, true);  //消费失败，并通知生产者
        }
    }


}
