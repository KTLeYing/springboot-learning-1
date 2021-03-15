package com.mzl.crossingdemo1.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   MyWebMvcConfig
 * @Description: 把拦截器注册到ebMvcConfig里面在前端请求时起作用，拦截
 * @Author: mzl
 * @CreateDate: 2020/11/28 15:06
 * @Version: 1.0
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ProcessInterceptor processInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(processInterceptor)
                .addPathPatterns("/**");
    }
}
