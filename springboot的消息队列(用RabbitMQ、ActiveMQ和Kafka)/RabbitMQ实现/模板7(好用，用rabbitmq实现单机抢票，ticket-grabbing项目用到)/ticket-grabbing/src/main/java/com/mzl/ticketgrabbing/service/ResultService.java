package com.mzl.ticketgrabbing.service;

/**
 * @InterfaceName :   ResultService
 * @Description: 结果业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:26
 * @Version: 1.0
 */
public interface ResultService {

    /**
     * 先判断该用户是否抢过该票
     * @param userId
     * @return
     */
    Boolean existByUserId(Integer userId);

    /**
     * 添加抢票记录
     * @param ticketId
     * @param userId
     */
    void addResult(Integer ticketId, Integer userId);
}
