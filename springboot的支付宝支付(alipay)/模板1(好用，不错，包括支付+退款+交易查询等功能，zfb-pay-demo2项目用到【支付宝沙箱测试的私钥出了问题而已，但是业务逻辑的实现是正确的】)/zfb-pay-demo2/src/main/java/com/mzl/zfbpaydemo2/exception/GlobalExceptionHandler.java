package com.mzl.zfbpaydemo2.exception;

import com.mzl.zfbpaydemo2.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :   GlobalExceptionHandler
 * @Description: 自定义异常监听器
 * @Author: mzl
 * @CreateDate: 2021/2/25 11:18
 * @Version: 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public Result customException(HttpServletRequest request, CustomException e){
        return Result.error(e.getCode(), e.getMessage());
    }

}
