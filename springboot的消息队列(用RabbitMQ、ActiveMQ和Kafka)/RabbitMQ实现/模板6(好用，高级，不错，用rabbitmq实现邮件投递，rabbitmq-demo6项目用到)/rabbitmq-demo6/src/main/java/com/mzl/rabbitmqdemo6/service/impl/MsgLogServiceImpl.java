package com.mzl.rabbitmqdemo6.service.impl;

import com.mzl.rabbitmqdemo6.dao.MsgLogDao;
import com.mzl.rabbitmqdemo6.pojo.MsgLog;
import com.mzl.rabbitmqdemo6.service.MsgLogService;
import com.mzl.rabbitmqdemo6.utils.JodaTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName :   MsgLogServiceImpl
 * @Description:
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:52
 * @Version: 1.0
 */
@Service
@Transactional
public class MsgLogServiceImpl implements MsgLogService {

    @Autowired
    private MsgLogDao msgLogDao;

    /**
     * 更新消息日志的状态
     * @param msgId
     * @param status
     */
    @Override
    public void updateStatus(String msgId, Integer status) {
        msgLogDao.updateStatus(msgId, status);
    }

    /**
     * 查询消息日志
     * @param msgId
     * @return
     */
    @Override
    public MsgLog selectByMsgId(String msgId) {
        return msgLogDao.selectByMsgId(msgId);
    }

    /**
     * 查询超时的消息
     */
    @Override
    public List<MsgLog> selectTimeoutMsg() {
        return msgLogDao.selectTimeoutMsg();
    }

    /**
     * 更新投递数，投递次数+1
     * @param msgId
     * @param nextTryTime1
     */
    @Override
    public void updateTryCount(String msgId, Date nextTryTime1) {
        Date nextTryTime = JodaTimeUtils.plusMinutes(nextTryTime1, 1);

        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setNextTryTime(nextTryTime);

        msgLogDao.updateTryCount(msgLog);
    }


}
