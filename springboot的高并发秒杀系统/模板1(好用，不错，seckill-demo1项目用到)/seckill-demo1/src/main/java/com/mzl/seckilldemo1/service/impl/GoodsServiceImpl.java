package com.mzl.seckilldemo1.service.impl;

import com.mzl.seckilldemo1.dao.GoodsDao;
import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.SeckillGoods;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.UserKey;
import com.mzl.seckilldemo1.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   GoodsServiceImpl
 * @Description: 商品业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/3/5 16:02
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    /**
     * 查询所有的商品
     * @return
     */
    @Override
    public List<GoodsDTO> listGoods() {
        return goodsDao.listGoods();
    }

    /**
     * 查询商品详情
     * @param
     * @param
     * @return
     */
    @Override
    public GoodsDTO detail(Integer goodsId) {
        return goodsDao.detail(goodsId);
    }

    /**
     * 减库存
     * @param goodsDTO
     * @return
     */
    @Override
    public boolean reduceStock(GoodsDTO goodsDTO) {
        int numAttempts = 0;
        int ret = 0;
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goodsDTO.getId());
        seckillGoods.setVersion(goodsDTO.getVersion());
        do{
            numAttempts++;
            try{
                seckillGoods.setVersion(goodsDao.getVersionByGoodsId(goodsDTO.getId()));
                ret = goodsDao.reduceStockByVersion(seckillGoods);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret != 0){
                break;
            }
        }while (numAttempts < DEFAULT_MAX_RETRIES);

        return ret > 0;
    }


}
