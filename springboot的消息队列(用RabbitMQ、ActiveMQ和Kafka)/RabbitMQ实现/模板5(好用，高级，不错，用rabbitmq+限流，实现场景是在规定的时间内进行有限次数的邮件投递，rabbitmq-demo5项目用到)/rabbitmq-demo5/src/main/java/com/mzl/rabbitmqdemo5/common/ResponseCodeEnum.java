package com.mzl.rabbitmqdemo5.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

/**
 * @EnumName :   ResponseCode
 * @Description: 响应的状态码
 * @Author: mzl
 * @CreateDate: 2020/10/22 9:53
 * @Version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor  //一定要有构造方法，把枚举变量里的成员变量属性与枚举类的属性相互对应上，连接起来
public enum ResponseCodeEnum {

    //系统模块
    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),
    SERVER_ERROR(500, "服务器异常"),

    //通用模块(1xxx)
    ILLEGAL_ARGUMENT(1000, "参数不合法"),
    REPETITIVE_OPERATION(1001, "请勿重复操作"),
    ACCESS_LIMIT(1002, "请求太频繁，请稍后重试"),
    MAIL_SEND_SUCCESS(1003, "邮件发送成功"),

    //用户模块（2xxx）
    NEED_LOGIN(2001, "登录失效"),
    USERNAME_OR_PASSWORD_EMPTY(2002, "用户名或密码为空"),
    USERNAME_OR_PASSWORD_WRONG(2003, "用户名或密码错误"),
    USER_NOT_EXISTS(2004, "用户不存在"),
    WRONG_PASSWORD(2005, "密码错误");


    private Integer code;
    private String msg;

}
