package com.mzl.seckilldemo1.service;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.UserKey;

import java.util.List;

/**
 * @InterfaceName :   GoodsService
 * @Description: goodsDao
 * @Author: mzl
 * @CreateDate: 2021/3/5 16:02
 * @Version: 1.0
 */
public interface GoodsService {

    //乐观锁冲突最大重试次数
    public static final int DEFAULT_MAX_RETRIES = 5;

    /**
     * 查询所有的商品
     * @return
     */
    List<GoodsDTO> listGoods();

    /**
     * 查询商品详情
     * @param
     * @param
     * @return
     */
    GoodsDTO detail(Integer goodsId);

    /**
     * 减库存
     * @param goodsDTO
     * @return
     */
    boolean reduceStock(GoodsDTO goodsDTO);
}
