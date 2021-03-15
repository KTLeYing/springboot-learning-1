package com.mzl.zfbpaydemo2.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.mzl.zfbpaydemo2.config.AlipayConfig;
import com.mzl.zfbpaydemo2.dao.OrderInfoDao;
import com.mzl.zfbpaydemo2.entity.OrderInfo;
import com.mzl.zfbpaydemo2.service.OrderInfoService;
import com.mzl.zfbpaydemo2.util.JsonUtil;
import com.mzl.zfbpaydemo2.util.RandomUtil;
import com.sun.deploy.util.SystemUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * @ClassName :   OrderInfoServiceImpl
 * @Description: 订单信息业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:48
 * @Version: 1.0
 */
@Slf4j
@Service
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private AlipayClient alipayClient;

    private List<String> statusList = Arrays.asList("WAIT_BUYER_PAY", "TRADE_CLOSED", "TRADE_SUCCESS", "TRADE_FINISHED");


    /**
     * 生成订单
     * @param subject
     * @param body
     * @param money
     * @param sellerId
     * @return
     */
    @Override
    public OrderInfo createOrder(String subject, String body, float money, String sellerId) {
        //生成商户订单号
        String orderId = RandomUtil.time();
        System.out.println("orderId：" + orderId);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setSubject(subject);
        orderInfo.setBody(body);
        orderInfo.setMoney(money);
        orderInfo.setSellerId(sellerId);
        orderInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
        orderInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        /**
         * 订单状态（与官方统一）
         * WAIT_BUYER_PAY：交易创建，等待买家付款；
         * TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；
         * TRADE_SUCCESS：交易支付成功；
         * TRADE_FINISHED：交易结束，不可退款
         */
        orderInfo.setStatus("WAIT_BUYER_PAY");
        //保存到数据库
        orderInfoDao.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 验证订单
     * @param params
     * @return
     */
    @Override
    public boolean validOrder(Map<String, String> params) throws AlipayApiException {
        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */

        // 1、调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), "utf-8", alipayConfig.getSignType());
        if (!signVerified){
            return false;
        }
        // 获取订单数据
        String orderId = params.get("out_trade_no");
        OrderInfo orderInfo = orderInfoDao.findById(orderId);
        if (orderInfo == null){
            return false;
        }
        // 2、判断金额是否相等
        float money = Float.parseFloat(params.get("total_amount"));
        if (money != orderInfo.getMoney()){
            return false;
        }
        // 3、判断商户ID是否相等
        String sellerId = params.get("seller_id");
        if (!sellerId.equals(orderInfo.getSellerId())){
            return false;
        }
        // 4、判断APP_ID是否相等
        String appId = params.get("app_id");
        if(!appId.equals(alipayConfig.getAppId())) {
            return false;
        }

        return true;
    }

    /**
     * 更新订单状态
     * @param orderId
     * @param status
     * @param tradeNo
     */
    @Override
    public boolean updateStatus(String orderId, String status, String... tradeNo) {
        // 判断参数是否合法
        OrderInfo orderInfo = orderInfoDao.findById(orderId);
        if (orderInfo == null){
            return false;
        }

        //要设置的状态不正确
        if (StringUtils.isBlank(status) || !statusList.contains(status)){
            return false;
        }

        // 如果订单状态相同，不发生改变
        if (status.equals(orderInfo.getStatus())){
            return true;
        }

        // 如果是TRADE_SUCCESS，设置订单号
        if ("TRADE_SUCCESS".equals(status) && tradeNo.length > 0){
            orderInfo.setAlipayNo(tradeNo[0]);
        }
        orderInfo.setStatus(status);
        orderInfoDao.updateById(orderInfo);
        return true;
    }

    /**
     * 通过id查询订单
     * @param outTradeNo
     * @return
     */
    @Override
    public OrderInfo findById(String outTradeNo) {
        return orderInfoDao.findById(outTradeNo);
    }

    /**
     * 通过订单号或支付宝交易号查询订单
     * @param orderId
     * @param alipayNo
     * @return
     */
    @Override
    public OrderInfo getByIdOrAlipayNo(String orderId, String alipayNo) {
        if (StringUtils.isNotBlank(orderId)){
            OrderInfo orderInfo = orderInfoDao.findById(orderId);
            if (orderInfo != null){
                return orderInfo;
            }
        }
        return null;
    }

    /**
     * 如果错误信息为 交易状态不合法 ，说明本地状态与服务器的不一致，需要手动同步
     * @param orderId
     * @param alipayNo
     */
    @Override
    public boolean syncStatus(String orderId, String alipayNo) {
        OrderInfo orderInfo = getByIdOrAlipayNo(orderId, alipayNo);
        if (orderInfo == null){
            return false;
        }
        // 1、设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        Map<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderId);
        map.put("trade_no", alipayNo);
        alipayRequest.setBizContent(JsonUtil.objectToJson(map));
        try {
            // 2、请求
            String json = alipayClient.execute(alipayRequest).getBody();
            Map<String, Object> resMap = JsonUtil.jsonToPojo(json, Map.class);
            Map<String, String> responseMap = (Map)resMap.get("alipay_trade_query_response");

            // 获得返回状态码，具体参考：https://docs.open.alipay.com/common/105806
            String code = responseMap.get("code");
            if("10000".equals(code)) {
                // 当查询的订单状态不等于数据库订单状态时，更新状态
                String tradeStatus = responseMap.get("trade_status");
                if(!orderInfo.getStatus().equals(tradeStatus)) {
                    orderInfo.setStatus(tradeStatus);
                    orderInfoDao.updateById(orderInfo);
                    return true;
                }
            } else {
                log.error("【状态同步Service】错误码：{}，错误信息：{}",code ,responseMap.get("sub_msg"));
            }
        } catch (Exception e) {
            log.error("【状态同步Service】异常，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
