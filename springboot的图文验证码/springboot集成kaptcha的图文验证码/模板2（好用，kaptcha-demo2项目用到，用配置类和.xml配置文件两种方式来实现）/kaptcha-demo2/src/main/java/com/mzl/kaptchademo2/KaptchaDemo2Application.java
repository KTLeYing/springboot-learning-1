package com.mzl.kaptchademo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:KaptchaConfig.xml"}) //加载资源配置文件，用@ImportResource来导入，相当于@configuration注解，注册到容器做原料
public class KaptchaDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(KaptchaDemo2Application.class, args);
    }

}
