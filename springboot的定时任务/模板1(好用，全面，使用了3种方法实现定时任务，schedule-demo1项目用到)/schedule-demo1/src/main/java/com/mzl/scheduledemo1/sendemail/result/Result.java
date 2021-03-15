package com.mzl.scheduledemo1.sendemail.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: 返回的结果
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:48
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
