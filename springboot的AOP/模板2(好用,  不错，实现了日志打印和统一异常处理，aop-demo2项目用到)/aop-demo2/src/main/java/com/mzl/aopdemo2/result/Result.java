package com.mzl.aopdemo2.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: 返回结果封装
 * @Author: mzl
 * @CreateDate: 2021/3/11 9:50
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
