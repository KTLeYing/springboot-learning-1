package com.mzl.activemqdemo2.exception;

import com.mzl.activemqdemo2.result.CodeEnum;
import com.mzl.activemqdemo2.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName :   MyExceptionHandler
 * @Description: 统一异常处理类(处理controller抛出的异常)
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:47
 * @Version: 1.0
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 自定义异常处理
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result myExceptionHandler(Exception e){
        MyException myException = (MyException) e;
        return new Result(myException.getCode(), myException.getMessage());
    }

    /**
     * 运行时异常处理
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result exceptionHandler(Exception e){
        return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
    }

}
