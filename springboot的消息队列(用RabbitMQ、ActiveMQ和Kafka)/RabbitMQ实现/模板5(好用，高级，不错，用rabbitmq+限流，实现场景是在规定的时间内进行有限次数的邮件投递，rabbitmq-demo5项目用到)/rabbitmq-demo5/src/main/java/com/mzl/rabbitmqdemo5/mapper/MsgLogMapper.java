package com.mzl.rabbitmqdemo5.mapper;

import com.mzl.rabbitmqdemo5.pojo.MsgLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :   MsgLogMapper
 * @Description: 消息日志mapper
 * @Author: mzl
 * @CreateDate: 2020/10/23 11:53
 * @Version: 1.0
 */
@Mapper
public interface MsgLogMapper {

    /**
     * 更新消息日志状态
     * @param msgId
     * @param status
     */
    void updateStatus(String msgId, Integer status);

    /**
     * 通过消息id来查询消息日志
     * @param msgId
     * @return
     */
    MsgLog selectByMsgId(String msgId);

    /**
     * 消息入库（写入要发送的消息到数据库）
     * @param msgLog
     */
    void insert(MsgLog msgLog);
}
