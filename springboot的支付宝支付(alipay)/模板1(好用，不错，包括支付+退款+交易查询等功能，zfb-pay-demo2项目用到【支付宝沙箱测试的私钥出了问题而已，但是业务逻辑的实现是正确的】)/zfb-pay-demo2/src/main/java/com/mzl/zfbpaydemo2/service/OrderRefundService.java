package com.mzl.zfbpaydemo2.service;

import com.mzl.zfbpaydemo2.entity.OrderRefund;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   OrderRefundService
 * @Description: 订单退回业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:49
 * @Version: 1.0
 */
public interface OrderRefundService {

    /**
     * 插入退款信息到数据库
     * @param refundId
     * @param reason
     * @param money
     * @param responseMap
     */
    void createRefund(String refundId, String reason, float money, Map<String, String> responseMap);

    /**
     * 查询所有的退款信息
     * @param orderId
     * @param refundId
     * @return
     */
    List<OrderRefund> listRefund(String orderId, String refundId);

}
