package com.mzl.ehcachedemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  //开启缓存，就可以使用缓存管理器
public class EhcacheDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(EhcacheDemo1Application.class, args);
    }

}
