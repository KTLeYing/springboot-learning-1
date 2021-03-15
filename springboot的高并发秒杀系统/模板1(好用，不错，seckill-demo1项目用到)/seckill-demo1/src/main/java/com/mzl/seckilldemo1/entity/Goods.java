package com.mzl.seckilldemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Goods
 * @Description: 商品实体类
 * @Author: mzl
 * @CreateDate: 2021/3/3 0:11
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
