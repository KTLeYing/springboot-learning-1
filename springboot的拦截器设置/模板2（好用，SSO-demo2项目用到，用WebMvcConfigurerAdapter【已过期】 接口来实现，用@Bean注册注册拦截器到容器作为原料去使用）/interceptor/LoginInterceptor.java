package com.mzl.SSOdemo2.interceptor;

import com.mzl.SSOdemo2.annotation.LoginAnnotation;
import com.mzl.SSOdemo2.util.CookieUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Handler;

/**
 * @ClassName :   LoginInterceptor
 * @Description: 单点登录拦截器(进行单点登录拦截验证)
 * @Author: mzl
 * @CreateDate: 2021/2/8 16:50
 * @Version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行了拦截器...");
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取请求方法上的注解
            LoginAnnotation loginAnnotation = handlerMethod.getMethodAnnotation(LoginAnnotation.class);
            if (loginAnnotation != null){
                //获取用户客户端cookie
                String cookieName = "TOKEN";
                String cookies = CookieUtils.getCookieValue(request, cookieName);
                if (StringUtils.isEmpty(cookies)){
                    System.out.println("请求拦截了...");
                    return false;    //如果cookies为空，则拦截
                }
                System.out.println("cookies还存在，不用重新登录，单点登录成功！cookies的值:" + cookies);
            }
            System.out.println("请求放行了...");
            return true;  //如果cookies不为空，则放行
        }
        System.out.println("请求放行了...");
        return true;
    }
}
