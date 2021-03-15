package com.mzl.rabbitmqdemo6.service.impl;

import com.mzl.rabbitmqdemo6.common.ResponseCode;
import com.mzl.rabbitmqdemo6.common.ServerResponse;
import com.mzl.rabbitmqdemo6.config.RabbitConfig;
import com.mzl.rabbitmqdemo6.dao.MsgLogDao;
import com.mzl.rabbitmqdemo6.pojo.Mail;
import com.mzl.rabbitmqdemo6.pojo.MsgLog;
import com.mzl.rabbitmqdemo6.service.TestService;
import com.mzl.rabbitmqdemo6.utils.MessageHelper;
import com.mzl.rabbitmqdemo6.utils.RandomUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   TestServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:54
 * @Version: 1.0
 */
@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private MsgLogDao msgLogDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 投递发送邮件
     * @param mail
     * @return
     */
    @Override
    public ServerResponse send(Mail mail){
        String msgId = RandomUtils.UUID32();
        mail.setMsgId(msgId);
        System.out.println("iii");
        System.out.println(mail);

        MsgLog msgLog = new MsgLog(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        //消息入库
        msgLogDao.insert(msgLog);
        System.out.println("uuu");
        System.out.println(msgLog);

        CorrelationData correlationData = new CorrelationData(msgId);
        //发送消息到对应的队列里面
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);

        return ServerResponse.success(ResponseCode.MAIL_SEND_SUCCESS.getMsg());  //返回成功的状态码和对应的消息
    }

}
