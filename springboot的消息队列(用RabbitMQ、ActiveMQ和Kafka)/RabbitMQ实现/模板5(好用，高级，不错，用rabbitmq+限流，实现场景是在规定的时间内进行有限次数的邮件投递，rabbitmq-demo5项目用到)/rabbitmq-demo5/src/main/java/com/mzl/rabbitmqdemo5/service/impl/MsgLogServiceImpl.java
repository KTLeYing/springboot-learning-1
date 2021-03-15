package com.mzl.rabbitmqdemo5.service.impl;

import com.mzl.rabbitmqdemo5.mapper.MsgLogMapper;
import com.mzl.rabbitmqdemo5.pojo.MsgLog;
import com.mzl.rabbitmqdemo5.service.MsgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   MsgLogServiceImpl
 * @Description: 消息日志业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2020/10/23 10:55
 * @Version: 1.0
 */
@Service
@Transactional
public class MsgLogServiceImpl implements MsgLogService {

    @Autowired
    private MsgLogMapper msgLogMapper;

    /**
     * 更新消息日志状态
     * @param msgId
     * @param deliverSuccess
     */
    @Override
    public void updateStatus(String msgId, Integer deliverSuccess) {
        msgLogMapper.updateStatus(msgId, deliverSuccess);
    }

    /**
     * 通过消息id来查询消息日志
     * @param correlationId
     * @return
     */
    @Override
    public MsgLog selectByMsgId(String correlationId) {
        return msgLogMapper.selectByMsgId(correlationId);
    }

}
