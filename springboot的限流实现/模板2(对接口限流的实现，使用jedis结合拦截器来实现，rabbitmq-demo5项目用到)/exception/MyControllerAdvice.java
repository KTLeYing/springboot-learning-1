package com.mzl.rabbitmqdemo5.exception;

import com.mzl.rabbitmqdemo5.common.ResponseCodeEnum;
import com.mzl.rabbitmqdemo5.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   MyControllerAdvice
 * @Description: 自定义异常处理类
 * @Author: mzl
 * @CreateDate: 2020/10/23 9:02
 * @Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    /**
     * 监听处理自定义的异常
     * @param e
     * @return
     */
    @ResponseBody    //返回异常消息到前端body区
    @ExceptionHandler(ServiceException.class)
    public ServerResponse serviceExceptionHandler(ServiceException e){
        return ServerResponse.error(e.getMsg());
    }

    /**
     * 监听处理运行时异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception e){
        return ServerResponse.error(ResponseCodeEnum.SERVER_ERROR.getMsg());
    }

}
