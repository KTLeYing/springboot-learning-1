package com.mzl.shirojwtSSO.exception;

import com.mzl.shirojwtSSO.result.ResultMap;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :   MyExceptionHandler
 * @Description: 自定义异常监听器处理器
 * @Author: mzl
 * @CreateDate: 2021/1/31 0:25
 * @Version: 1.0
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 监听捕获shiro异常
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public ResultMap handle401(ShiroException e){
        return new ResultMap(401, e.getMessage() + ",您没有权限访问！", null);
    }

    /**
     * 单独捕获Shiro的（UnauthorizedException）异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常，用户权限认证失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ResultMap handle401(AuthorizationException e){
        return new ResultMap(HttpStatus.UNAUTHORIZED.value(), "无权访问,当前的Subject没有请求的权限(" + e.getMessage() + ")", null);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常，用户认证失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResultMap handle401(AuthenticationException e) {
        return new ResultMap(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请先登录(This subject is anonymous.)", null);
    }


    /**
     * 其他异常的监听
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultMap globalException(HttpServletRequest request, Throwable ex){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        int code = 0;
        if (statusCode == null) {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }else {
            code = HttpStatus.valueOf(statusCode).value();
        }
        return new ResultMap(code, "访问出错，无法访问:" + ex.getMessage(), null);
    }

    /**
     * 自定义的异常监听器
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public ResultMap myException(MyException e){
        return new ResultMap(e.getCode(), e.getMessage(), null);
    }


}
