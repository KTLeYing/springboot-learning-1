package com.mzl.seckilldemo1.controller;

import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.service.GoodsService;
import com.mzl.seckilldemo1.service.OrderService;
import com.mzl.seckilldemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName :   OrderController
 * @Description: 订单控制器
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:00
 * @Version: 1.0
 */
@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;



}
