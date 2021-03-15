package com.mzl.rabbitmqdemo6.consumer;

import com.mzl.rabbitmqdemo6.common.Constant;
import com.mzl.rabbitmqdemo6.config.RabbitConfig;
import com.mzl.rabbitmqdemo6.pojo.Mail;
import com.mzl.rabbitmqdemo6.pojo.MsgLog;
import com.mzl.rabbitmqdemo6.result.Result;
import com.mzl.rabbitmqdemo6.service.MailService;
import com.mzl.rabbitmqdemo6.service.MsgLogService;
import com.mzl.rabbitmqdemo6.utils.MessageHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.ChannelN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName :   MailConsumer
 * @Description: MailConsumer消费消息, 发送邮件
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:56
 * @Version: 1.0
 */
@Component
@Slf4j
public class MailConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        //把message转换为mail实体类
        Mail mail =  MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息: {}", mail.toString());

        String msgId = mail.getMsgId();

        //查询消息日志
        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if (msgLog == null || msgLog.getStatus().equals(Constant.MsgLogStatus.DELIVER_SUCCESS)){  //消费等幂性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }

        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();    //获取发送的标志

        //发送邮件
        Result result = mailService.sendCommonEmail(mail);
        //判断是否发送邮件成功
        if (result.getCode() == 200){
            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(tag, false);   //消费确认
        }else {
            /*
             *验证消费端发生异常消息也不会丢失很显然, 消费端代码可能发生异常, 如果不做处理, 业务没正确执行, 消息却不见了, 给我们感觉就是消息丢失了, 由于我们消费端代码做了异常捕获, 业务异常时,
             * 会触发: channel.basicNack(tag, false, true);, 这样会告诉rabbitmq该消息消费失败, 需要重新入队, 可以重新投递到其他正常的消费端进行消费, 从而保证消息不被丢失
             * 可以看到, 由于channel.basicNack(tag, false, true), 未被ack的消息(unacked)会重新入队并被消费, 这样就保证了消息不会走丢
             */
            channel.basicNack(tag, false, true);  //发送失败
        }
    }

}
