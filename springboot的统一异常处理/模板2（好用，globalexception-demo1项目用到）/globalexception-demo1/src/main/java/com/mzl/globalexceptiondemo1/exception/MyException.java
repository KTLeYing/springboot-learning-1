package com.mzl.globalexceptiondemo1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   MyException
 * @Description: 自定义的异常类，继承运行时异常类
 * @Author: mzl
 * @CreateDate: 2020/10/11 20:31
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 4564124491192825748L;
    private int code;

    /**
     * 构造方法
     */
    public MyException(int code, String message) {
        super(message);
        this.code = code;
    }

}
