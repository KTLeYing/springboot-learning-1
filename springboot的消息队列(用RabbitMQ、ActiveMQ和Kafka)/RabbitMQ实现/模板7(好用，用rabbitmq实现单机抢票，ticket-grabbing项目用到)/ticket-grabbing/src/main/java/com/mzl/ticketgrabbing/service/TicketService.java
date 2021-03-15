package com.mzl.ticketgrabbing.service;

import com.mzl.ticketgrabbing.entity.Ticket;

import java.util.Optional;

/**
 * @InterfaceName :   TicketService
 * @Description: 票接口
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:28
 * @Version: 1.0
 */
public interface TicketService {

    /**
     * 减少库存操作
     * @param ticketId
     * @return
     */
    int reduceCount(Integer ticketId);

    /**
     * 查询当前票的剩余数量
     * @param ticketId
     * @return
     */
    Optional<Ticket> findCount(Integer ticketId);
}
