package com.mzl.securitydemo7.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @EnumName :   CodeEnum
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:48
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeEnum {

    SUCCESS(200, "短信发送成功"),
    FAIL(201, "短信发送失败"),
    NOT_ALLOW_SEND(202, "短信还存在，暂时不能发送"),
    SERVER_ERROR(500, "系统错误"),
    REQUEST_ERROR(404, "请求异常");

    private Integer code;
    private String msg;

}
