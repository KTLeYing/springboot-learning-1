package com.mzl.shirojwt.exception;

import lombok.Data;

/**
 * @ClassName :   MYException
 * @Description: 自定义的异常
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:22
 * @Version: 1.0
 */
public class MyException extends RuntimeException {

    private int code;

    public MyException(String message){
        super(message);
    }

    public MyException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
