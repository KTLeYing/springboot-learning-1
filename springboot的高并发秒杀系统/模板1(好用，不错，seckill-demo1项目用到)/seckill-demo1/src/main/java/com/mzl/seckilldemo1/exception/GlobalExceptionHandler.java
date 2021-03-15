package com.mzl.seckilldemo1.exception;

import com.mzl.seckilldemo1.result.Result;
import com.mzl.seckilldemo1.result.ResultCode;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import java.util.List;

/**
 * @ClassName :   GlobalExceptionHandler
 * @Description: 全局自定义异常控制器
 * @Author: mzl
 * @CreateDate: 2021/3/3 9:05
 * @Version: 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException globalException = (GlobalException) e;
            return new Result(globalException.getMsg());
        }else if (e instanceof BindException){
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return new Result(msg);//给状态码填充参数
        }else {
            return new Result(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg());
        }
    }

}
