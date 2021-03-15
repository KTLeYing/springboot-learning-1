package com.mzl.updateimage.exception;

import lombok.Getter;

/**
 * @EnumName :   SysError
 * @Description: 系统错误类型
 * @Author: mzl
 * @CreateDate: 2021/1/2 11:26
 * @Version: 1.0
 */
@Getter
public enum SysError {

    SYSTEM_ERROR(1, "系统异常"),
    SYSTEM_BUSY(2, "系统繁忙中，请稍后再试"),
    SYSTEM_PRIMARY_KEY(3, "唯一键冲突");

    private int code;
    private String msg;

    SysError(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
