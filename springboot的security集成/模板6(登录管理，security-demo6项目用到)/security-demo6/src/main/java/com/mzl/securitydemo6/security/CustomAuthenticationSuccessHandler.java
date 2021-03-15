package com.mzl.securitydemo6.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   CustomAuthenticationSuccessHandler
 * @Description: 认证成功处理,自定义 CustomAuthenticationSuccessHandler 类来实现 AuthenticationSuccessHandler 接口，用来处理认证成功后逻辑：
 * @Author: mzl
 * @CreateDate: 2020/11/14 19:50
 * @Version: 1.0
 */
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功,{}", authentication);

        //登录成功后重定向到首页
        response.sendRedirect("/home");
    }
}
