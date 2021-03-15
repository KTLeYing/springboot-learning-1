package com.mzl.zfbpaydemo2.dao;

import com.mzl.zfbpaydemo2.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @InterfaceName :   OrderInfoDao
 * @Description: 订单信息dao
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:53
 * @Version: 1.0
 */
@Mapper
public interface OrderInfoDao {

    /**
     * 保存到数据库
     * @param orderInfo
     */
    void insert(OrderInfo orderInfo);

    /**
     *  获取订单数据
     * @param orderId
     * @return
     */
    @Select("select * from order_info where order_id = #{orderId}")
    OrderInfo findById(String orderId);

    /**
     * 更新订单状态
     * @param orderInfo
     */
    void updateById(OrderInfo orderInfo);
}
