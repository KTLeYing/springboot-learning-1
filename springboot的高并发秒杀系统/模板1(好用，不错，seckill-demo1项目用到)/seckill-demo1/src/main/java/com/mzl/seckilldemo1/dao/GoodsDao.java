package com.mzl.seckilldemo1.dao;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName :   GoodsDao
 * @Description: 商品dao
 * @Author: mzl
 * @CreateDate: 2021/3/5 16:03
 * @Version: 1.0
 */
@Mapper
public interface GoodsDao {

    /**
     * 查询所有的商品
     * @return
     */
    List<GoodsDTO> listGoods();

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    GoodsDTO detail(Integer goodsId);

    /**
     * 通过商品id获取商版本
     * @param goodsId
     * @return
     */
    int getVersionByGoodsId(Long goodsId);

    /**
     * 通过版本信息减少库存
     * @param seckillGoods
     * @return
     */
    int reduceStockByVersion(SeckillGoods seckillGoods);
}
