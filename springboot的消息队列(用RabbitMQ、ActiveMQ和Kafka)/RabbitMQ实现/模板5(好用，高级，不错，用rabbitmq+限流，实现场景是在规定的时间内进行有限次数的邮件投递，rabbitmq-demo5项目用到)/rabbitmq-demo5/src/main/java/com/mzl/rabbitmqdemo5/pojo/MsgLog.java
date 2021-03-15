package com.mzl.rabbitmqdemo5.pojo;

import com.mzl.rabbitmqdemo5.common.Constant;
import com.mzl.rabbitmqdemo5.utils.JodaTimeUtils;
import com.mzl.rabbitmqdemo5.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   MsgLog
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/22 9:08
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgLog {

    private String msgId;
    private String msg;
    private String exchange;
    private String routingKey;
    private Integer status;
    private Integer tryCount;
    private Date nextTryTime;
    private Date createTime;
    private Date updateTime;

    public MsgLog(String msgId, Object msg, String exchange, String routingKey){
        this.msgId = msgId;
        this.msg = JsonUtils.objToStr(msg);  //包括邮件所有的详细信息（Mail实体类）
        this.exchange = exchange;
        this.routingKey = routingKey;

        this.status = Constant.MsgLogStatus.DELIVERING;
        this.tryCount = 0;

        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;

        this.nextTryTime = JodaTimeUtils.plusMinutes(date, 1);
    }

}
