package com.mzl.seckilldemo1.entity;

import com.alibaba.druid.filter.AutoLoad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   SeckillOrder
 * @Description: 秒杀的订单
 * @Author: mzl
 * @CreateDate: 2021/3/3 0:14
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillOrder {

    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;

}
