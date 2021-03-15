package com.mzl.aopdemo2.exception;

import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @ClassName :   MyException
 * @Description: 自定义的异常
 * @Author: mzl
 * @CreateDate: 2021/3/11 9:50
 * @Version: 1.0
 */
@Data
public class GlobalException extends RuntimeException {

    private Integer code;

    public GlobalException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

}
