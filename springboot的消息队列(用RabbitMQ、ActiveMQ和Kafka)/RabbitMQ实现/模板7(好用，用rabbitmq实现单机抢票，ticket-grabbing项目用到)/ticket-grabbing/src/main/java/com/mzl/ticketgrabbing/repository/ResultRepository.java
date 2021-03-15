package com.mzl.ticketgrabbing.repository;

import com.mzl.ticketgrabbing.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName :   ResultRepository
 * @Description: 结果repository
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:31
 * @Version: 1.0
 */
public interface ResultRepository extends JpaRepository<Result, Integer> {

    /**
     * 先判断该用户是否抢过该票
     * @param userId
     * @return
     */
    Boolean existsByUserId(Integer userId);
}
