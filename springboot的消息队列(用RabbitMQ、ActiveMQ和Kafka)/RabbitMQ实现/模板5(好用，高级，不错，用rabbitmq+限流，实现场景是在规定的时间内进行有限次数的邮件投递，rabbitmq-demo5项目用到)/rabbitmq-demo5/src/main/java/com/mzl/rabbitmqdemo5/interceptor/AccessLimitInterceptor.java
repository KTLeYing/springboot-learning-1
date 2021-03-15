package com.mzl.rabbitmqdemo5.interceptor;

import com.mzl.rabbitmqdemo5.annotation.AccessLimit;
import com.mzl.rabbitmqdemo5.common.Constant;
import com.mzl.rabbitmqdemo5.common.ResponseCodeEnum;
import com.mzl.rabbitmqdemo5.config.JedisConfig;
import com.mzl.rabbitmqdemo5.exception.ServiceException;
import com.mzl.rabbitmqdemo5.utils.IpUtils;
import com.mzl.rabbitmqdemo5.utils.JedisUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName :   AccessLimitInterceptor
 * @Description: 接口防刷限流拦截器
 * @Author: mzl
 * @CreateDate: 2020/10/23 9:15
 * @Version: 1.0
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisUtils jedisUtils;

    /**
     * 处理controller接口前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器开始执行...");
        if (!(handler instanceof HandlerMethod)){   //不是处理器要处理的实例
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        System.out.println("annotation is: " + annotation);
        if (annotation != null){
            System.out.println("kkk");
            check(annotation, request);
        }

        return true;
    }

    /**
     * 对自定义注解和请求进行具体检查处理，看是否要放行请求
     * @param annotation
     * @param request
     */
    public void check(AccessLimit annotation, HttpServletRequest request) throws ServletException {
        int maxCount = annotation.maxCount();
        int seconds = annotation.seconds();
        System.out.println(maxCount);
        System.out.println(seconds);

        StringBuilder sb = new StringBuilder();
        sb.append(Constant.Redis.ACCESS_LIMIT_PREFIX).append(IpUtils.getIpAddress(request)).append(request.getRequestURI());
        String key = sb.toString();
        System.out.println("key:" + key);

        Boolean exist = jedisUtils.exist(key);
        if (!exist){  //不存在
            System.out.println("iii");
            jedisUtils.set(key, String.valueOf(1), seconds);
        }else {
            System.out.println("www");
            int count = Integer.valueOf(jedisUtils.get(key));  //获取key的值
            System.out.println(jedisUtils.get(key));
            System.out.println("count is:" + count);
            if (count < maxCount){
                System.out.println("jjj");
                Long ttl = jedisUtils.ttl(key);
                if (ttl <= 0){
                    System.out.println("uuu");
                    jedisUtils.set(key, String.valueOf(1), seconds);    //ttl小于等于0，设值为1
                }else {
                    System.out.println("yyy");
                    jedisUtils.set(key, String.valueOf(++count), ttl.intValue());  //请求一次，值加1
                }
            }else {
                System.out.println("hhh");
                throw new ServiceException(ResponseCodeEnum.ACCESS_LIMIT.getMsg());   //抛出自定义的异常，自定义异常监听器会监听并处理的
//                throw new ServletException(ResponseCodeEnum.ACCESS_LIMIT.getMsg());
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         System.out.println("拦截器执行结束，controller接口成功执行了，放行了请求....");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
