package com.mzl.rabbitmqdemo5.config;

import com.mzl.rabbitmqdemo5.annotation.AccessLimit;
import com.mzl.rabbitmqdemo5.interceptor.AccessLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName :   InterceptorConfig
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/23 18:10
 * @Version: 1.0
 */
@Configuration
public class LimitInterceptorConfig extends WebMvcConfigurerAdapter{

    @Bean   //注入拦截器，使用原料
    public AccessLimitInterceptor accessLimitInterceptor(){
        return new AccessLimitInterceptor();
    }

    /**
     * 注册拦截器到webMvc，配置拦截路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

}
