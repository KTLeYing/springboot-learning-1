package com.mzl.securitydemo7.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:47
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private String  smsCode;
    private T data;

    public Result(Integer code, String msg, String smsCode) {
        this.code = code;
        this.msg = msg;
        this.smsCode = smsCode;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
