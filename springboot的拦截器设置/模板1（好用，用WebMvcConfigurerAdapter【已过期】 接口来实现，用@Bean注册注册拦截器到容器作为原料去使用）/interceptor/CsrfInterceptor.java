package com.jobgo.gateway.interceptor;

import com.jobgo.gateway.properties.CsrfReleaseProperties;
import com.jobgo.sms.properties.SmsProperties;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName :   CsrfInterceptor
 * @Description: 自定义csrf拦截器（实现HandlerInterceptor接口）
 * @Author: mzl
 * @CreateDate: 2020/9/22 10:45
 * @Version: 1.0
 */
//@Component: 作用是将当前类实例化到spring容器中，相当于xml配置文件中的<bean id="" class=""/>
@Component
@Slf4j
@EnableConfigurationProperties   //可以注入属于配置属性的依赖
public class CsrfInterceptor implements HandlerInterceptor {

    //注入依赖来使用
    @Autowired
    private CsrfReleaseProperties csrfReleaseProperties;

    /**
     * controller方法之前进行执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //默认值为false，代表不继续往后执行。如果为true，即使拦截了也依然会执行被拦截请求对应的controller层对应的方法。因此为true和没加拦截没区别，
        // 如果为false的话，被拦截的请求不会被执行它对应的controller方法
        System.out.println("拦截器开始执行...");

        // 从 HTTP 头中取得 Referer 值
        String referer = request.getHeader("Referer");
        System.out.println("referer:" + referer);

        //如果获取的referer为空，则拦截请求
        if (referer == null){
            return false;
        }

        List<String> domains = csrfReleaseProperties.getReleaseDomainList();
        System.out.println("domainList:" + domains);

        for (String domain : domains) {
            // 判断 Referer 是否以***域名开头
            System.out.println(domain);
            System.out.println(referer.trim().endsWith(domain));
            if (referer.trim().endsWith(domain)){
                return true;
            }
        }

        return false;
    }

    /**
     * 请求处理之后进行调用，Controller方法调用之后执行（拦截结束后）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         System.out.println("controller调用成功，拦截结束，没有拦截请求路径...");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行，无论拦截器有无异常都执行
     * 用于清理资源
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
