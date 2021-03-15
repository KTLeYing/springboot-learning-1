package com.mzl.rabbitmqdemo6.dao;

import com.mzl.rabbitmqdemo6.pojo.MsgLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName :   dao
 * @Description: 消息日志dao
 * @Author: mzl
 * @CreateDate: 2020/11/27 14:08
 * @Version: 1.0
 */
@Mapper
public interface MsgLogDao {

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
     * 消息入库
     * @param msgLog
     */
    void insert(MsgLog msgLog);

    /**
     * 查询消息的超时时间
     * @return
     */
    List<MsgLog> selectTimeoutMsg();

    /**
     * 更新投递数，投递次数+1
     * @param msgLog
     * @param
     */
    void updateTryCount(MsgLog msgLog);
}
