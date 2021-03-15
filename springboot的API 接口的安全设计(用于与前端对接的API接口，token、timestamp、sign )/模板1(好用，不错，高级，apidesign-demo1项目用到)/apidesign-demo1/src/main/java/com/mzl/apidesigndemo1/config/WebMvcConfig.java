package com.mzl.apidesigndemo1.config;

import com.mzl.apidesigndemo1.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   WebMvcConfig
 * @Description: web的MVC页面处理配置类
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:13
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final String[] excludePathPatterns = {"/api/token/apiToken"};

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 把自定义的拦截器注册到WebMvc页面中去使用
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns(excludePathPatterns);
    }
}
