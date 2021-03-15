package com.mzl.securitydemo7.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName :   MyException
 * @Description: 自定义异常
 * @Author: mzl
 * @CreateDate: 2020/10/25 10:50
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{

    private Integer code;

    /**
     * 继承父类的构造方法，即只含有message的构造方法
     * @param message
     */
    public MyException(String message) {
        super(message);
    }

    /**
     *  构造方法重载，继承了运行时异常父类的构造方法，即继承了包含一个message参数的构造方法
     * @param message
     * @param code
     */
    public MyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
