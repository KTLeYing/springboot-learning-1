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
     * 限流测试
     * @return
     */
    @Override
    public ServerResponse accessLimit() {
        return ServerResponse.success("accessLimit: success");
    }

     /**
     * 幂等性测试
     * @return
     */
    @Override
    public ServerResponse testIdempotence() {
        return ServerResponse.success("testIdempotence: success");
    }

   


}
