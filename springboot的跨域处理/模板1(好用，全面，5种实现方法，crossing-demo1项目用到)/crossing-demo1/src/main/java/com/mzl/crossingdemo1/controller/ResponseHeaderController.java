//package com.mzl.crossingdemo1.controller;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @ClassName :   ResponseHeaderController
// * @Description: 后端跨域处理方法5: 手工设置响应头（HttpServletResponse ）,可以自己手工加到，具体的controller，inteceptor，filter等逻辑里。
// *               （与自定义的过滤器类似 MyCorsFilter类）
// * @Author: mzl
// * @CreateDate: 2020/10/26 2:08
// * @Version: 1.0
// */
//@RestController
//public class ResponseHeaderController {
//
//    @RequestMapping("/responseHeader")
//    public String responseHeader(HttpServletResponse response){
//        response.addHeader("Access-Control-Allow-Origin", "*");  // *号表示对所有请求都允许跨域访问,全局跨域
////        response.addHeader("Access-Control-Allow-Origin", "https://www.baidu.com");  //局部跨域
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Methods", "*");  //所有请求
////        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
//
//        return "success";
//    }
//
//}
