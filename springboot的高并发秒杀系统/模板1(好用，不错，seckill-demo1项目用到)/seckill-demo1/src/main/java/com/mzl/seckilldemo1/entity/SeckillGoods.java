package com.mzl.seckilldemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   SeckillGoods
 * @Description: 秒杀商品实体类
 * @Author: mzl
 * @CreateDate: 2021/3/3 0:13
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private int version;


}
