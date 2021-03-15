package com.mzl.rabbitmqdemo5.service.impl;

import com.mzl.rabbitmqdemo5.common.ResponseCodeEnum;
import com.mzl.rabbitmqdemo5.common.ServerResponse;
import com.mzl.rabbitmqdemo5.config.RabbitConfig;
import com.mzl.rabbitmqdemo5.mapper.MsgLogMapper;
import com.mzl.rabbitmqdemo5.mq.MessageHelper;
import com.mzl.rabbitmqdemo5.pojo.Mail;
import com.mzl.rabbitmqdemo5.pojo.MsgLog;
import com.mzl.rabbitmqdemo5.service.TestService;
import com.mzl.rabbitmqdemo5.utils.RandomUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @ClassName :   TestServiceImpl
 * @Description: 测试业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/10/23 12:14
 * @Version: 1.0
 */
@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private MsgLogMapper msgLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 幂等性测试
     * @return
     */
    @Override
    public ServerResponse testIdempotence() {
        return ServerResponse.success("testIdempotence: success");
    }

    /**
     * 限流测试
     * @return
     */
    @Override
    public ServerResponse accessLimit() {
        return ServerResponse.success("accessLimit: success");
    }

    /***
     * 发送邮件
     * @param mail
     * @return
     */
    @Override
    public ServerResponse send(Mail mail) {
        String msgId = RandomUtils.UUID32();
        mail.setMsgId(msgId);

        MsgLog msgLog = new MsgLog(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        // 消息入库（写入要发送的消息到数据库）
        msgLogMapper.insert(msgLog);

        //产生发送消息id，唯一标识
        CorrelationData correlationId = new CorrelationData(msgId);
        //生产者开始发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationId);

        return ServerResponse.success(ResponseCodeEnum.MAIL_SEND_SUCCESS.getMsg());
    }


}
