package com.mzl.SSOdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//该注解会扫描相应的包
@ServletComponentScan
//或
//@ServletComponentScan(basePackages = "com.mzl.SSOdemo2.filter")//所扫描的包路径必须包含该Filter
public class SsoDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(SsoDemo2Application.class, args);
	}

}
