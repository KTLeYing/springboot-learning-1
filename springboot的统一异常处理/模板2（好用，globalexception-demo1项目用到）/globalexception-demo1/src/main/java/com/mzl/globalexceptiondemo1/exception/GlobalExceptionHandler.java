package com.mzl.globalexceptiondemo1.exception;

import com.mzl.globalexceptiondemo1.utils.ErrorResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @ClassName : GlobalExceptionHandler
 * @Description: 自定义统一异常处理类定（重点，核心）
 * @Author: mzl @CreateDate: 2020/10/11 20:47
 * @Version: 1.0
 *
 * 自定义统一异常处理类中会用到如下一些关键的注解，概述如下：
 * @ControllerAdvice：捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON格式。
 * @RestControllerAdvice：相当于 @ControllerAdvice 与 @ResponseBody的结合体,以json的格式返回。
 * @ExceptionHandler：统一处理一种类的异常，减少代码重复率，降低复杂度。
 * 创建一个 GlobalExceptionHandler
 * 类，并添加上 @RestControllerAdvice 注解就可以定义出异常通知类了，然后在定义的方法中添加上 @ExceptionHandler 即可实现异常的捕捉。
 */
@RestControllerAdvice // 相当于@ResponseBody + @ControllerAdvice  ，捕获处理了异常并输出到response的body区
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 定义要捕获的异常 可以同时定义多个 @ExceptionHandler({})数组的形式
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public ErrorResponseUtils myExceptionHandler(HttpServletRequest request, HttpServletResponse response, final Exception e){
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        //捕获处理自定义的异常
        MyException myException = (MyException) e;
        return new ErrorResponseUtils(myException.getCode(), myException.getMessage());   //直接调用构造方法
    }

    /**
     * 捕获 RuntimeException 异常
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseUtils runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, final Exception e){
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        //捕获处理运行时异常
        RuntimeException runtimeException = (RuntimeException) e;
        return new ErrorResponseUtils(400, runtimeException.getMessage());
    }

    /**
     * 通用的接口映射异常处理方，ResponseEntity<Object>相当于@ResponseBody或response对象，用于返回响应到前端的body区，都是继承和遵从http协议的相关标准和规则
     * @param ex
     * @param body
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new ErrorResponseUtils(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
        }

        if (ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" +
                    exception.getName() + "，信息：" + exception.getMessage());
            return new ResponseEntity<>(new ErrorResponseUtils(status.value(), "参数转换失败"), status);
        }

        return new ResponseEntity<>(new ErrorResponseUtils(status.value(), "参数转换错误"), status);
    }

}
