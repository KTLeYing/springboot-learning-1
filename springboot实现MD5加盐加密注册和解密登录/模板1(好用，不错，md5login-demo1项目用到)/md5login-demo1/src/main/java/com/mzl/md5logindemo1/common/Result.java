package com.mzl.md5logindemo1.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: 返回的结果封装
 * @Author: mzl
 * @CreateDate: 2020/11/27 18:33
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean flag;
    private Integer code;
    private String message;
    private Object date;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public boolean isFlag(){
        return flag;
    }

}
