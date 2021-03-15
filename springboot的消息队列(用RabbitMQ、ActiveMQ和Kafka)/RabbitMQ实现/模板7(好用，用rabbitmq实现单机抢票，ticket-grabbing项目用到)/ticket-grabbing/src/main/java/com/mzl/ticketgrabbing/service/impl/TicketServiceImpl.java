package com.mzl.ticketgrabbing.service.impl;

import com.mzl.ticketgrabbing.entity.Ticket;
import com.mzl.ticketgrabbing.repository.TicketRepository;
import com.mzl.ticketgrabbing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName :   TicketServiceImpl
 * @Description: 票业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:29
 * @Version: 1.0
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * 减少库存操作
     * @param ticketId
     * @return
     */
    @Override
    public int reduceCount(Integer ticketId) {
        return ticketRepository.reduceCount(ticketId);
    }

    /**
     * 查询当前票的剩余数量
     * @param ticketId
     * @return
     */
    @Override
    public Optional<Ticket> findCount(Integer ticketId) {
        return ticketRepository.findById(ticketId);
    }
}
