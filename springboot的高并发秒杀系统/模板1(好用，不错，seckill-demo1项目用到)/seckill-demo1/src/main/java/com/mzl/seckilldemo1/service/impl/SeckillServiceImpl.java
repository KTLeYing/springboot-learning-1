package com.mzl.seckilldemo1.service.impl;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.OrderInfo;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.redis.redis.SeckillKey;
import com.mzl.seckilldemo1.service.GoodsService;
import com.mzl.seckilldemo1.service.OrderService;
import com.mzl.seckilldemo1.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   SeckillServiceImpl
 * @Description: 秒杀业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/3/5 22:50
 * @Version: 1.0
 */
@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    /**
     * 保证这三个操作，减库存 下订单 写入秒杀订单是一个事物
     * @param user
     * @param goodsDTO
     */
    @Override
    public OrderInfo seckill(User user, GoodsDTO goodsDTO) {
        //减库存
        boolean success = goodsService.reduceStock(goodsDTO);
        if (success){
            //下订单 写入秒杀订单
            return orderService.createOrder(user, goodsDTO);
        }else {
            //已经超买完，不能抢购,设置redis缓存
            setGoodsOver(goodsDTO.getId());
            return null;
        }
    }

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder seckillOrder = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (seckillOrder != null){
            return seckillOrder.getId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }

    /**
     * 已经超买完，不能抢购,并设置超卖的redis
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }

    /**
     * 获取超卖的redis
     * @param goodsId
     */
    private boolean getGoodsOver(Long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

}
