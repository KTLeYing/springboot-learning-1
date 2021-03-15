package com.mzl.zfbpaydemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   OrderRefund
 * @Description: 订单退回实体类·
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:43
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退款号
     */
    private String refundId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 退款金额
     */
    private float money;
    /**
     * 退款账户
     */
    private String account;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 退款时间
     */
    private String refundDate;

}
