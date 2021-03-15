package com.mzl.authcssodemo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   WebInterceptorConfig
 * @Description: 前端页面请求的拦截器的配置，注册自定义拦截器到前端web请求配置器
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:58
 * @Version: 1.0
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    /**
     *  防止redis报空，在context前初始化,注入自定义拦截器使起作用
     */
    @Bean
    public LoginInterceptor getLoginHandlerInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * 注册添加拦截器到web请求前端去拦截请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getLoginHandlerInterceptor());
        registration.addPathPatterns("/**");  //拦截的路径
        registration.excludePathPatterns("/user/login", "/user/error/**");  //放行的路径
    }
}
