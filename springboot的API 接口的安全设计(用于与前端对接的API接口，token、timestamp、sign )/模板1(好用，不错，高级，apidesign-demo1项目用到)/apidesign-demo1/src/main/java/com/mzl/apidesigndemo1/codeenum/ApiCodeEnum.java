package com.mzl.apidesigndemo1.codeenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @EnumName :   ApiCodeEnum
 * @Description: api状态码枚举诶
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:54
 * @Version: 1.0
 */
@Getter
public enum ApiCodeEnum {

    SUCCESS("10000", "success"),
    UNKNOW_ERROR("ERR0001","未知错误"),
    PARAMETER_ERROR("ERR0002","参数错误"),
    TOKEN_EXPIRE("ERR0003","认证过期"),
    REQUEST_TIMEOUT("ERR0004","请求超时"),
    SIGN_ERROR("ERR0005","签名错误"),
    REPEAT_SUBMIT("ERR0006","请不要频繁操作");

    private String code;
    private String msg;

    ApiCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
