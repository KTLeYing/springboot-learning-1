package com.mzl.rabbitmqdemo5.service;

import com.mzl.rabbitmqdemo5.pojo.MsgLog;

/**
 * @InterfaceName :   MsgLogService
 * @Description: 消息日志业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/10/23 10:54
 * @Version: 1.0
 */
public interface MsgLogService {

    /**
     * 更新消息日志状态
     * @param msgId
     * @param deliverSuccess
     */
    void updateStatus(String msgId, Integer deliverSuccess);

    /**
     * 通过消息id来查询消息日志
     * @param correlationId
     * @return
     */
    MsgLog selectByMsgId(String correlationId);
}
