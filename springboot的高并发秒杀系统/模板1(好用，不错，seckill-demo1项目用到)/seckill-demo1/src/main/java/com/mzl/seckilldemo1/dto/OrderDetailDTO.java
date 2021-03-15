package com.mzl.seckilldemo1.dto;

import com.mzl.seckilldemo1.entity.OrderInfo;

/**
 * Created by jiangyunxiong on 2018/5/28.
 */
public class OrderDetailDTO {
    private GoodsDTO goods;
    private OrderInfo order;
    public GoodsDTO getGoods() {
        return goods;
    }
    public void setGoods(GoodsDTO goods) {
        this.goods = goods;
    }
    public OrderInfo getOrder() {
        return order;
    }
    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
