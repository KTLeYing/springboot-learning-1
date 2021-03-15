package com.mzl.securitydemo6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//使在程序入口就执行，开始起作用，先判断浏览器session和redis存储的情况，使spring容器能够支持对http请求session的redis共享，
// 会在浏览器生成一个用户登录状态的session，多个端口访问共享一个session（同一浏览器才行），使生效起作用
@EnableRedisHttpSession
@SpringBootApplication
public class SecurityDemo6Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo6Application.class, args);
    }

}
