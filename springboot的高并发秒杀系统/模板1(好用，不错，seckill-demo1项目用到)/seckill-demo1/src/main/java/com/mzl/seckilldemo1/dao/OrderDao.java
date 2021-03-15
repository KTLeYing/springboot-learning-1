package com.mzl.seckilldemo1.dao;

import com.mzl.seckilldemo1.entity.OrderInfo;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   OrderDao
 * @Description: 订单dao
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:59
 * @Version: 1.0
 */
@Mapper
public interface OrderDao {

    /**
     * 创建订单
     * @param orderInfo
     */
    long insert(OrderInfo orderInfo);

    /**
     * 创建秒杀订单
     * @param seckillOrder
     */
    long insertSeckillOrder(SeckillOrder seckillOrder);

    /**
     * 通过goodsId和userId查询orderId
     * @param userId
     * @param goodsId
     * @return
     */
    long getOrderId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

}
