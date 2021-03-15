package com.mzl.seckilldemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   OrderInfo
 * @Description: 订单详情
 * @Author: mzl
 * @CreateDate: 2021/3/3 0:12
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

}
