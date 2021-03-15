package com.mzl.jwtdemo3.config;

import com.mzl.jwtdemo3.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   WebConfig
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/11 12:15
 * @Version: 1.0
 */
@Configuration  //注册到spring容器
public class WebConfig implements WebMvcConfigurer {

    // WebMvcConfigurerAdapter 这个类在SpringBoot2.0已过时，官方推荐直接实现WebMvcConfigurer 这个接口

    /**
     *  注册拦截器到容器作为原料去使用
     */
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    /**
     * 注册拦截器添加到webMVC中去使用，进行路径连拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor;
        InterceptorRegistration jwtInterceptorRegistration = registry.addInterceptor(jwtInterceptor());
        //配置拦截器的拦截和放行规则
        jwtInterceptorRegistration.addPathPatterns("/jwt/**")
                .excludePathPatterns("/jwt/login");
    }

}
