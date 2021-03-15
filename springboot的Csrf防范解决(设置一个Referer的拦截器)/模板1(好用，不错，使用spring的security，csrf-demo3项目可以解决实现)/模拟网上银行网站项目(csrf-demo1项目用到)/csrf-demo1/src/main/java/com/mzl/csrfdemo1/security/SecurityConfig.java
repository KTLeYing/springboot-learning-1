package com.mzl.csrfdemo1.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName :   SecurityConfig
 * @Description: security配置类
 * @Author: mzl
 * @CreateDate: 2021/3/11 15:59
 * @Version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf()   //关闭csrf功能，security默认开启csrf功能
                .disable();
    }

}
