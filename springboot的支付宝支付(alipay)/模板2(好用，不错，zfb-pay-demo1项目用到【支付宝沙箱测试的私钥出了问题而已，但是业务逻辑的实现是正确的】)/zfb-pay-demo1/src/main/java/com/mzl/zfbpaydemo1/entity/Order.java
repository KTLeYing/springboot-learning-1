package com.mzl.zfbpaydemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Order
 * @Description: 订单实体类
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:47
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;
    private String username;  //支付的用户名
    private String commodity;  //商品名称
    private String description;  //商品描述
    private String tradeNo;   //支付宝交易号
    private String outTradeNo;   //商户订单号
    private Float money;   //支付的金额
    private Integer status;   //支付状态(0:未支付  1：支付成功)

}
