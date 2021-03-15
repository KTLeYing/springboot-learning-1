package com.mzl.globalexceptiondemo2.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   MyException
 * @Description: 自定义异常，为了区分系统异常和更方便系统的特定一些处理
 * @Author: mzl
 * @CreateDate: 2020/10/13 20:58
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException {

    private Integer code;

    /**
     * 继承父类的构造方法，即只含有message的构造方法
     * @param message
     */
    public MyException(String message) {
        super(message);
    }

    /**
     * 构造方法重载，继承了运行时异常父类的构造方法，即继承了包含一个message参数的构造方法
     */
    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
