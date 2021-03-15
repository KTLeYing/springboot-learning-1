package com.mzl.zfbpaydemo1.dao;

import com.mzl.zfbpaydemo1.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName :   OrderDa
 * @Description: 订单dao
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:52
 * @Version: 1.0
 */
@Mapper
public interface OrderDao {

    /**
     * 添加订单，保存到数据库
     * @param order
     */
    void addOrder(Order order);

    /**
     * 查询所有的订单
     * @return
     */
    @Select("select * from order_info")
    List<Order> findAllOrder();

    /**
     * 通过商家订单号查询订单
     * @param outTradeNo
     * @return
     */
    @Select("select * from order_info where out_trade_no = #{outTradeNo}")
    Order findByOutTradeNo(String outTradeNo);

    /**
     * 保存到数据库(更新订单信息)
     * @param order
     */
    void updateOrder(Order order);
}
