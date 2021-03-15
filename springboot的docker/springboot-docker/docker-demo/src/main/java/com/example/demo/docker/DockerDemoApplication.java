package com.example.demo.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerDemoApplication.class, args);
	}

	/**
	 * 方法名：
	 * 功能：此程序不能运行在Windows下，在linux运行
	 * 描述：
	 * 创建人：typ
	 * 创建时间：2018/10/17 17:01
	 * 修改人：
	 * 修改描述：
	 * 修改时间：
	 */
	@GetMapping("/test")
	public String test(){

		return "Hello Docker World!";
	}
}
