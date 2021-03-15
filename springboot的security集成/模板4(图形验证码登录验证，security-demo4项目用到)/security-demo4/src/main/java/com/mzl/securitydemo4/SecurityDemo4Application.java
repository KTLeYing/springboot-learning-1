package com.mzl.securitydemo4;

import com.mzl.securitydemo4.servlet.VerifyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityDemo4Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo4Application.class, args);
    }

    /**
     * 注入验证码servlet依赖bean，使起作用,业间接是验证码起作用，在启动时自动加载调用显示验证码
     */
    @Bean
    public ServletRegistrationBean indexServletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new VerifyServlet());
        registrationBean.addUrlMappings("/getVerifyCode");   //这个路径会和servlet的匿名url设置的路径器匹配，允许该路径请求
        return registrationBean;
    }

}
