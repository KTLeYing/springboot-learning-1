package com.mzl.rabbitmqdemo6.service;

import com.mzl.rabbitmqdemo6.pojo.MsgLog;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName :   MsgLogService
 * @Description: 消息日志业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:51
 * @Version: 1.0
 */
public interface MsgLogService {

    /**
     * 更新消息日志的状态
     * @param msgId
     * @param status
     */
    void updateStatus(String msgId, Integer status);

    /**
     * 查询消息日志
     * @param msgId
     * @return
     */
    MsgLog selectByMsgId(String msgId);

    /**
     * 查询超时的消息
     * @return
     */
    List<MsgLog> selectTimeoutMsg();

    /**
     *     更新投递数，投递次数+1
     */
    void updateTryCount(String msgId, Date nextTryTime);
}
