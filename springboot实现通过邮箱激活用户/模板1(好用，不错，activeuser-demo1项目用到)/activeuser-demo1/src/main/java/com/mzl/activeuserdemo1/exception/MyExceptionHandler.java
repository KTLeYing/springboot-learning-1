package com.mzl.activeuserdemo1.exception;

import com.mzl.activeuserdemo1.result.CodeEnum;
import com.mzl.activeuserdemo1.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName :   ExceptionHandler
 * @Description: 统一异常处理器类
 * @Author: mzl
 * @CreateDate: 2020/10/24 20:32
 * @Version: 1.0
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(){
        return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
    }

}
