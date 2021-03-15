package com.mzl.updateimage.result;

import lombok.Getter;

/**
 * @EnumName :   ResultCode1
 * @Description: 返回状态码
 * @Author: mzl
 * @CreateDate: 2021/1/2 13:05
 * @Version: 1.0
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    SERVER_ERROR(500, "服务器异常"),
    USER_NOT_EXISTS(201, "用户名或密码错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
