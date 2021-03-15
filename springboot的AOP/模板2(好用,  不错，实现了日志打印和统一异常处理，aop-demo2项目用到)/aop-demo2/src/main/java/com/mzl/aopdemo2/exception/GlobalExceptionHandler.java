package com.mzl.aopdemo2.exception;

import com.mzl.aopdemo2.result.Result;
import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName :   GlobalExceptionHandler
 * @Description: 统一异常控制器
 * @Author: mzl
 * @CreateDate: 2021/3/11 11:01
 * @Version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常控制器
     * @param e
     * @return
     */
    @ExceptionHandler(value = GlobalException.class)
    public Result globalException(Exception e){
        GlobalException globalException = (GlobalException) e;
        return new Result(globalException.getCode(), globalException.getMessage());
    }

    /**
     * 运行时异常控制器
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result exception(Exception e){
        RuntimeException runtimeException = (RuntimeException) e;
        return new Result(500, runtimeException.getMessage());
    }

}
