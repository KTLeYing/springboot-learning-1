package com.mzl.kaptchademo1;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName :   ServletInitializer
 * @Description: 服务器初始化类
 * @Author: mzl
 * @CreateDate: 2020/10/25 19:29
 * @Version: 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(KaptchaDemo1Application.class);
    }
}
