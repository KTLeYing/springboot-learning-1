package com.mzl.seckilldemo1.service;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.OrderInfo;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import com.mzl.seckilldemo1.entity.User;

/**
 * @InterfaceName :   OrderService
 * @Description: 订单业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:05
 * @Version: 1.0
 */
public interface OrderService {

    /**
     * 通过用户id和商品id获取商品
     * @param id
     * @param goodsId
     * @return
     */
    SeckillOrder getOrderByUserIdGoodsId(Long id, long goodsId);

    /**
     * 下订单 写入秒杀订单
     * @param user
     * @param goodsDTO
     * @return
     */
    OrderInfo createOrder(User user, GoodsDTO goodsDTO);

}
