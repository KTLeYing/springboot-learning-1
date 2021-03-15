package com.mzl.rabbitmqdemo5.common;

/**
 * @ClassName :   Constant
 * @Description: 统一常量定义
 * @Author: mzl
 * @CreateDate: 2020/10/22 9:38
 * @Version: 1.0
 */
public class Constant {

    public interface Redis{
        String OK = "OK";
        Integer EXPIRE_TIME_MINUTE = 60;  //过期时间60s
        Integer EXPIRE_TIME_HOUR = 60 * 60;  //过期时间，一小时（化为秒）
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;  //过期时间，一天（化为秒）
        String TOKEN_PREFIX = "token";
        String MSG_CONSUMER_PREFIX = "consumer";
        String ACCESS_LIMIT_PREFIX = "accessLimit";
    }

    public interface LogType{
        Integer LOGIN = 1; //登录
        Integer LOGOUT = 2;  //登出

    }

    public interface MsgLogStatus{
        Integer DELIVERING = 0;  //投递中
        Integer DELIVER_SUCCESS = 1; //投递成功
        Integer DELIVER_FAIL = 2;  //投递失败
        Integer CONSUMED_SUCCESS = 3;  //已成功消费
    }


}
