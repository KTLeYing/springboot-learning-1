package com.mzl.seckilldemo1.service.impl;

import com.mzl.seckilldemo1.dao.OrderDao;
import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.OrderInfo;
import com.mzl.seckilldemo1.entity.SeckillGoods;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.OrderKey;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName :   OrderServiceImpl
 * @Description: 订单业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:05
 * @Version: 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;


    /**
     * 通过用户id和商品id获取商品
     * @param
     * @param goodsId
     * @return
     */
    @Override
    public SeckillOrder getOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
    }

    /**
     * 下订单 写入秒杀订单,因为要同时分别在订单详情表和秒杀订单表都新增一条数据，所以要保证两个操作是一个事物
     * @param user
     * @param goodsDTO
     * @return
     */
    @Override
    public OrderInfo createOrder(User user, GoodsDTO goodsDTO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsDTO.getId());
        orderInfo.setGoodsName(goodsDTO.getGoodsName());
        orderInfo.setGoodsPrice(goodsDTO.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderInfoId = orderDao.insert(orderInfo);

        //通过goodsId和userId查询orderId
        long orderId = orderDao.getOrderId(orderInfo.getUserId(), orderInfo.getGoodsId());
        System.out.println(orderId);
        System.out.println("jjj");

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsDTO.getId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);

        //设置抢购redis缓存
        redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goodsDTO.getId(), seckillOrder);
        return orderInfo;
    }




}
