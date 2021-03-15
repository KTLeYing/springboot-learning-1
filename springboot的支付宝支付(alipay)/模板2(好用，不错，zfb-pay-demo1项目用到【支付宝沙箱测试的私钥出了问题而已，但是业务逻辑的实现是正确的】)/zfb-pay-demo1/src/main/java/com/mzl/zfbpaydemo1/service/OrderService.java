package com.mzl.zfbpaydemo1.service;

import com.mzl.zfbpaydemo1.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @InterfaceName :   OrderService
 * @Description: 订单业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:51
 * @Version: 1.0
 */
public interface OrderService {

    /**
     * 支付
     * @param order
     * @param request
     * @param response
     */
    void pay(Order order, HttpServletRequest request, HttpServletResponse response);

    /**
     *  //从数据库查询所有的订单
     * @return
     */
    List<Order> findAllOrder();

    /***
     * 通过商家订单号查询订单
     * @param outTradeNo
     * @return
     */
    Order findByOutTradeNo(String outTradeNo);

    /**
     * 保存到数据库(更新订单信息)
     * @param order
     */
    void updateOrder(Order order);

}
