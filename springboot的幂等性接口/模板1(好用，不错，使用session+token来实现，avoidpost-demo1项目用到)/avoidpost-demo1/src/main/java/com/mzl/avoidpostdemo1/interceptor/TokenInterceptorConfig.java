package com.mzl.avoidpostdemo1.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName :   Inter
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 16:02
 * @Version: 1.0
 */
@Configuration
public class TokenInterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 把自定义的拦截器添加到spring容器中作为原料
     * @return
     */
    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    /**
     * 注册拦截器，使起作用，专门拦截后端的访问接口
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/order/**")  //拦截的路径
                .excludePathPatterns("/static/**");  //放行的资源和路径

        super.addInterceptors(registry);
    }
}
