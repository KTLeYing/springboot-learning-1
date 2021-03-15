package com.mzl.zfbpaydemo2.dao;

import com.mzl.zfbpaydemo2.entity.OrderRefund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName :   OrderRefund
 * @Description: 订单退回dsao
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:53
 * @Version: 1.0
 */
@Mapper
public interface OrderRefundDao {

    /**
     * 添加退款记录
     * @param orderRefund
     */
    void insert(OrderRefund orderRefund);

    /**
     * 查询所有的退款信息
     * @param
     * @param refundId
     * @return
     */
    List<OrderRefund> selectList(@Param("orderId") String orderId, @Param("refundId") String refundId);
}
