package com.mzl.redisdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(RedisDemo1Application.class, args);
	}

}
