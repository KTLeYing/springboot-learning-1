package com.mzl.SSOdemo2.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   FilterConfig
 * @Description: 过滤器配置类（代替了在启动类上添加@ServletComponentScan注解，）
 * @Author: mzl
 * @CreateDate: 2021/2/8 23:04
 * @Version: 1.0
 */
@Configuration
public class FilterConfig {

    /**
     * 过滤器注册（使起作用）
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LoginFilter());
        bean.addUrlPatterns("/*");  //拦截所有路径
        return bean;
    }


}
