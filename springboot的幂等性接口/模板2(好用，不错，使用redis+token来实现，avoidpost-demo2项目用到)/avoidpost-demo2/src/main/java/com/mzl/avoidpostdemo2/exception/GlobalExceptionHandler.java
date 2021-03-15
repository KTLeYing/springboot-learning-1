package com.mzl.avoidpostdemo2.exception;

import com.mzl.avoidpostdemo2.result.ResultMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   GlabalExceptionHandler
 * @Description: 统一异常处理类
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:11
 * @Version: 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMap comException(Exception e){
        e.printStackTrace();
        return new ResultMap().error();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultMap myException(BaseException e){
        e.printStackTrace();
        return new ResultMap(e.getCode(), e.getMsg(), null);
    }

}
