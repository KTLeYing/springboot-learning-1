package com.mzl.zfbpaydemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName :   OrderInfo
 * @Description: 订单实体类
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:42
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单名称
     */
    private String subject;
    /**
     * 订单描述
     */
    private String body;
    /**
     * 付款金额
     */
    private float money;
    /**
     * 商户UID
     */
    private String sellerId;
    /**
     * 支付宝订单号
     */
    private String alipayNo;

    /**
     * 订单状态（与官方统一）
     * WAIT_BUYER_PAY：交易创建，等待买家付款；
     * TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；
     * TRADE_SUCCESS：交易支付成功；
     * TRADE_FINISHED：交易结束，不可退款
     */
    private String status;

    /**
     * 总计退款金额
     */
    private float refundMoney;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;

}
