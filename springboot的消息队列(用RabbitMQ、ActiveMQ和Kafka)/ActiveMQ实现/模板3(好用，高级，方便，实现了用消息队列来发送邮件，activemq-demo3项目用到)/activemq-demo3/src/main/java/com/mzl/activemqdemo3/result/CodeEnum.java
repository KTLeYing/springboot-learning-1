package com.mzl.activemqdemo3.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @EnumName :   CodeEnum
 * @Description: 返回的状态码
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:49
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeEnum {

    SUCCESS(200, "邮件发送成功"),
    FAIL(201, "邮件发送失败"),
    SERVER_ERROR(500, "服务器错误"),
    REQUEST_ERROR(404, "请求错误");

    private int code;
    private String msg;

}
