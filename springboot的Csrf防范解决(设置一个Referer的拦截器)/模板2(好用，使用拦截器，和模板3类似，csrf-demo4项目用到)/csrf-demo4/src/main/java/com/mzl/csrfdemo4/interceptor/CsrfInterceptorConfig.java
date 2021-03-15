package com.mzl.csrfdemo4.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName :   InterceptorConfig
 * @Description: 拦截器配置类
 * @Author: mzl
 * @CreateDate: 2021/3/11 21:56
 * @Version: 1.0
 */
@Configuration
public class CsrfInterceptorConfig extends WebMvcConfigurerAdapter {

    /**
     * 将自定义的拦截器注入到容器中
     * @return
     */
    @Bean
    public CsrfInterceptor csrfInterceptor(){
        return new CsrfInterceptor();
    }

    /**
     * 添加注册拦截器,使在web层起作用
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(csrfInterceptor())
                .addPathPatterns("/*")   //配置拦截路径
                .excludePathPatterns("/login", "/toIndex");   //配置放行的路径
        super.addInterceptors(registry);
    }

}
