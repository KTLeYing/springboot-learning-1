package com.mzl.seckilldemo1.service;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.OrderInfo;
import com.mzl.seckilldemo1.entity.User;

/**
 * @InterfaceName :   SeckillService
 * @Description: 秒杀业务罗锦
 * @Author: mzl
 * @CreateDate: 2021/3/5 22:50
 * @Version: 1.0
 */
public interface SeckillService {

    /**
     * 减少库存， 下订单， 写入秒杀订单
     * @param user
     * @param goodsDTO
     */
    OrderInfo seckill(User user, GoodsDTO goodsDTO);

    /**
     * 获取秒杀结果
     * @param id
     * @param goodsId
     * @return
     */
    long getSeckillResult(Long id, long goodsId);

}
