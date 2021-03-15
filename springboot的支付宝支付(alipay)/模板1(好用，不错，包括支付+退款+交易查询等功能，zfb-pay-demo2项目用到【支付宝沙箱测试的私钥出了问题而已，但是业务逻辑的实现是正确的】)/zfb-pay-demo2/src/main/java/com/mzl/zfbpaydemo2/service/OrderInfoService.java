package com.mzl.zfbpaydemo2.service;

import com.alipay.api.AlipayApiException;
import com.mzl.zfbpaydemo2.entity.OrderInfo;

import java.util.Map;

/**
 * @InterfaceName :   OrderInfoService
 * @Description: 订单信息业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:48
 * @Version: 1.0
 */
public interface OrderInfoService {

    /**
     * 生成订单
     * @param subject
     * @param body
     * @param money
     * @param sellerId
     * @return
     */
    OrderInfo createOrder(String subject, String body, float money, String sellerId);

    /**
     * //验证订单
     * @param params
     * @return
     */
    boolean validOrder(Map<String, String> params) throws AlipayApiException;

    /**
     * 更新状态
     * @param orderId
     * @param status
     * @param tradeNo
     */
    boolean updateStatus(String orderId, String status, String... tradeNo);

    /**
     * 通过id查询订单
     * @param outTradeNo
     * @return
     */
    OrderInfo findById(String outTradeNo);

    /**
     * 通过订单号或支付宝交易号查询订单
     * @param orderId
     * @param alipayNo
     * @return
     */
    OrderInfo getByIdOrAlipayNo(String orderId, String alipayNo);

    /**
     * 如果错误信息为 交易状态不合法 ，说明本地状态与服务器的不一致，需要手动同步
     * @param orderId
     * @param alipayNo
     */
    boolean syncStatus(String orderId, String alipayNo);
}
