package com.mzl.crossingdemo1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : CorsController
 * @Description: 后端跨域处理方法4：使用 @CrossOrigin 注解，Controller层在需要跨域的类或者方法上加上该注解即可。
 * @Author: mzl @CreateDate: 2020/10/26 1:54 @Version: 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //跨域实现注解（对所有接口跨域）,不配置方法，默认允许所有方法
//@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST}) //跨域实现注解（对所有接口跨域）
//局部跨域
//@CrossOrigin(origins = {"https://blog.csdn.net", "http://localhost:8080"}, maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST})
public class CorsController {

    @RequestMapping("/crossOrigin")
//    @CrossOrigin(origins = "*", maxAge = 3600) //跨域实现注解（对此接口跨域）
    public String crossOrigin(){
        return "success";
    }


}
