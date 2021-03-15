package com.mzl.seckilldemo1.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.rabbitmq.MQSender;
import com.mzl.seckilldemo1.rabbitmq.SeckillMessage;
import com.mzl.seckilldemo1.redis.redis.GoodsKey;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.result.Result;
import com.mzl.seckilldemo1.result.ResultCode;
import com.mzl.seckilldemo1.service.GoodsService;
import com.mzl.seckilldemo1.service.OrderService;
import com.mzl.seckilldemo1.service.SeckillService;
import com.mzl.seckilldemo1.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   SeckillController
 * @Description: 秒杀控制器
 * @Author: mzl
 * @CreateDate: 2021/3/5 22:32
 * @Version: 1.0
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MQSender mqSender;

    //基于令牌桶算法的限流实现类
    RateLimiter rateLimiter = RateLimiter.create(10);

    //做标记，判断该商品是否被处理过了
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * GET POST
     * 1、GET幂等,服务端获取数据，无论调用多少次结果都一样
     * 2、POST，向服务端提交数据，不是幂等
     * <p>
     * 将同步下单改为异步下单
     * @return
     */
    @RequestMapping("/doSeckill")
    @ResponseBody
    public Result doSeckill(Model model, User user, long goodsId){
        System.out.println(goodsId);
        //限流
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            return new Result(ResultCode.ACCESS_LIMIT_REACHED.getCode(), ResultCode.ACCESS_LIMIT_REACHED.getMsg(), null);
        }
        System.out.println(user);
        if (user == null){
            return new Result(ResultCode.SESSION_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg(), null);
        }
        model.addAttribute("user", user);
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        System.out.println(over);
        if (over){
            return new Result(ResultCode.SECKILL_OVER.getCode(), ResultCode.SECKILL_OVER.getMsg(), null);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
        if (stock < 0){
            localOverMap.put(goodsId, true);
            return new Result(ResultCode.SECKILL_OVER.getCode(), ResultCode.SECKILL_OVER.getMsg(), null);
//            afterPropertiesSet();
//            long stock2 = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
//            if (stock2 < 0){
//                localOverMap.put(goodsId, true);
//                return new Result(ResultCode.SECKILL_OVER.getCode(), ResultCode.SECKILL_OVER.getMsg(), null);
//            }
        }
        //判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null){
            return new Result(ResultCode.REPEATE_SECKILL.getCode(), ResultCode.REPEATE_SECKILL.getMsg(), null);
        }
        //入队，生产者发送消息
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        mqSender.sendSeckillMessage(message);
        return new Result(200, "秒杀成功", 0);
    }

    /**
     * 系统初始化,将商品信息加载到redis和本地内存
     */
    @Override
    public void afterPropertiesSet() {
        System.out.println("lll");
        List<GoodsDTO> goodsDTOList = goodsService.listGoods();
        if (goodsDTOList == null){
            return;
        }
        for (GoodsDTO goodsDTO : goodsDTOList){
            redisService.set(GoodsKey.getGoodsStock, "" + goodsDTO.getId(), goodsDTO.getStockCount());
            //初始化商品都是没有处理过的
            localOverMap.put(goodsDTO.getId(), false);
        }
    }


    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping("/result")
    @ResponseBody
    public Result seckillResult(Model model, User user, long goodsId){
        model.addAttribute("user", user);
        if (user == null){
            return new Result(ResultCode.SESSION_ERROR.getCode(), ResultCode.SESSION_ERROR.getMsg(), null);
        }
        long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        return new Result(200, "秒杀结果", orderId);
    }


}
