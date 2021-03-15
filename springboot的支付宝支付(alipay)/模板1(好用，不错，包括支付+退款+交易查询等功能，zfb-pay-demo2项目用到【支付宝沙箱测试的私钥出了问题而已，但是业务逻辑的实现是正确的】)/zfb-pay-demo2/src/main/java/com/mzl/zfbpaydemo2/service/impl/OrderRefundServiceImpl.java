package com.mzl.zfbpaydemo2.service.impl;

import com.mzl.zfbpaydemo2.dao.OrderInfoDao;
import com.mzl.zfbpaydemo2.dao.OrderRefundDao;
import com.mzl.zfbpaydemo2.entity.OrderRefund;
import com.mzl.zfbpaydemo2.service.OrderRefundService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   OrderRefundServiceImpl
 * @Description: 订单退回业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:52
 * @Version: 1.0
 */
@Service
@Transactional
public class OrderRefundServiceImpl implements OrderRefundService {

    @Autowired
    private OrderRefundDao orderRefundDao;
    @Autowired
    private OrderInfoDao orderInfoDao;


    /**
     * 插入退款信息到数据库
     * @param refundId
     * @param reason
     * @param money
     * @param map
     */
    @Override
    public void createRefund(String refundId, String reason, float money, Map<String, String> map) {
        // 退款账户
        String buyerLogonId = map.get("buyer_logon_id");
        // 已退款总金额
        float refundFee = Float.parseFloat(map.get("refund_fee"));
        // 退款订单号
        String outTradeNo = map.get("out_trade_no");
        // 退款时间
        String gmtRefundPay = map.get("gmt_refund_pay");

        OrderRefund orderRefund = new OrderRefund(refundId,outTradeNo,money,buyerLogonId,reason,gmtRefundPay);
        // 1、添加退款记录
        orderRefundDao.insert(orderRefund);

    }

    /**
     * 查询所有的退款信息
     * @param orderId
     * @param refundId
     * @return
     */
    @Override
    public List<OrderRefund> listRefund(String orderId, String refundId) {
        if(StringUtils.isBlank(refundId) && StringUtils.isBlank(orderId)) {
            return null;
        }
        return orderRefundDao.selectList(orderId, refundId);
    }

}
