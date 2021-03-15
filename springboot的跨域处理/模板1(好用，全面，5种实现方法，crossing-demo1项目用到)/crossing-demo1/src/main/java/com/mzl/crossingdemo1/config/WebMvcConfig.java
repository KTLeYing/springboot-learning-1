//package com.mzl.crossingdemo1.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @ClassName :   CorsConfig1
// * @Description: 后端跨域处理方法3：重写WebMvcConfigurer的addCorsMappings方法,直接在spring容器里作为原料并使用原料起作用了
// * @Author: mzl
// * @CreateDate: 2020/10/26 1:48
// * @Version: 1.0
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //设置允许跨域的路径
//        registry.addMapping("/**")  //监听所有的请求路径,/**
//                //设置允许跨域请求的域名
//                //当**Credentials为true时，**Origin不能为星号，需为具体的ip地址【如果接口不带cookie,ip无需设成具体ip】
//                .allowedOrigins("*") // *是代表所有的外部域都可跨域访问。 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
////                .allowedOrigins("https://blog.csdn.net", "http://127.0.0.1:9527", "http://127.0.0.1:8082", "http://127.0.0.1:8083")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
////                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                //跨域允许时间
//                .maxAge(3600);
//
//    }
//
//}
