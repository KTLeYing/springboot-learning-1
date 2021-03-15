package com.fsl.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fsl.dubboapi.service.UserService;
import com.fsl.dubboconsumer.dto.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    /**
     * version:接口的版本管理，可以通过这个对一个接口进行多版本控制
     * cluster：集群容错，dubbo提供了五种集群容错模式，默认是failover，失败自动切换
     * check：启动的时候是否对服务进行检查，默认是true
     * async：是否支付异步调用，默认是false
     * timeout：接口的最大响应时间，消费端的优先级大于服务提供端的优先级
     * loadbalance：负载均衡模式，默认是随机模式
     */
    @Reference(version = "${demo.service.version}",cluster = "failover",check = false,async = true,timeout = 5000,loadbalance = "random")
    private UserService userService;

    @ResponseBody
    @GetMapping("/getUser")
    public ServiceResult getUser(){
        return ServiceResult.success(userService.getUser());
    }
}
