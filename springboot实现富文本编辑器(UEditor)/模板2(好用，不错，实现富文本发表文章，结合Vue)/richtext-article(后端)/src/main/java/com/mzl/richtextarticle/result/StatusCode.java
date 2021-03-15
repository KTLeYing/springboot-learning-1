package com.mzl.richtextarticle.result;

import lombok.Getter;

/**
 * @EnumName :   StatusCode
 * @Description: 返回状态码
 * @Author: mzl
 * @CreateDate: 2021/1/6 10:19
 * @Version: 1.0
 */
@Getter
public enum StatusCode {

    SUCCESS(200, "操作成功"),
    FAIL(201, "操作失败"),
    SERVER_ERROR(500, "服务器错误");

    private int code;
    private String msg;

    StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
