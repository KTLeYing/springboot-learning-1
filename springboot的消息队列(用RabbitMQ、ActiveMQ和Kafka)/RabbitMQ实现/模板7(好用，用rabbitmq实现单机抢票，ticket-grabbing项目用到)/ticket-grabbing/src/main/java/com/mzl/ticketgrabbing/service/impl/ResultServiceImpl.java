package com.mzl.ticketgrabbing.service.impl;

import com.mzl.ticketgrabbing.entity.Result;
import com.mzl.ticketgrabbing.repository.ResultRepository;
import com.mzl.ticketgrabbing.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   ResultServiceImpl
 * @Description: 结果业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:27
 * @Version: 1.0
 */
@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    /**
     * 先判断该用户是否抢过该票
     * @param userId
     * @return
     */
    @Override
    public Boolean existByUserId(Integer userId) {
        return resultRepository.existsByUserId(userId);
    }

    /**
     * 添加抢票记录
     * @param ticketId
     * @param userId
     */
    @Override
    public void addResult(Integer ticketId, Integer userId) {
        Result result = new Result();
        result.setTicketId(ticketId);
        result.setUserId(userId);
        resultRepository.save(result);
    }
}
