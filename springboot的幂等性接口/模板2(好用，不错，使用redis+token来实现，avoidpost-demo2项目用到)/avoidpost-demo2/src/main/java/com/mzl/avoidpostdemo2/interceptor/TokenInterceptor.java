package com.mzl.avoidpostdemo2.interceptor;

import com.mzl.avoidpostdemo2.annotation.AutoIdempotent;
import com.mzl.avoidpostdemo2.service.RedisService;
import com.mzl.avoidpostdemo2.service.TokenService;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName :   TokenInterceptor
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:38
 * @Version: 1.0
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            Method method = ((HandlerMethod) handler).getMethod();
            AutoIdempotent autoIdempotent = method.getAnnotation(AutoIdempotent.class);   //获取方法上的注解
            if (autoIdempotent != null){  //方法上有注解，则执行判断token逻辑
                return tokenService.checkToken(request);
            }
        }

        return true;
    }
}
