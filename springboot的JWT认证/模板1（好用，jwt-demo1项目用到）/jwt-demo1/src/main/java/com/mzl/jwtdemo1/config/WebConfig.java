package com.mzl.jwtdemo1.config;

import com.mzl.jwtdemo1.interceptor.TokenInterceptor;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   WebConfig
 * @Description: 注册拦截器到SpringMvc去使用，对各种请求路径进行拦截过滤
 * @Author: mzl
 * @CreateDate: 2020/10/9 16:12
 * @Version: 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 注册拦截器和要拦截的路径到SpringMvc去使用
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**"); //拦截所有路径，包括多级，用/**
    }


}
