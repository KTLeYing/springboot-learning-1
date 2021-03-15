package com.mzl.SSOdemo1.config;

import com.mzl.SSOdemo1.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName :   WebConfig
 * @Description: 前端页面请求的页面配置，注册拦截器来拦截处理请求
 * @Author: mzl
 * @CreateDate: 2021/1/30 10:33
 * @Version: 1.0
 */
@Service
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AccessInterceptor accessInterceptor;

    /**
     * 注册拦截器到web前端的请求页面来拦截处理请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/**")   //拦截的路径
                .excludePathPatterns("");   //放行的路径
    }
}
