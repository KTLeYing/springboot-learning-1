package com.mzl.avoidpostdemo2.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   TokenInterceptorConfig
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:49
 * @Version: 1.0
 */
@Configuration
public class TokenInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 把自定义的拦截器注册到webmvcConfigurer，去拦截前端传到控制器接口的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }

}
