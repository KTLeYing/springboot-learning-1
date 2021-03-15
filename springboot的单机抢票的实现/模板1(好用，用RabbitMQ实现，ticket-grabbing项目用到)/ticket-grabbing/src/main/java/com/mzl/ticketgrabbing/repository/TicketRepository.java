package com.mzl.ticketgrabbing.repository;

import com.mzl.ticketgrabbing.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @InterfaceName :   TicketRepository
 * @Description: 票的repository
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:32
 * @Version: 1.0
 */
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    /**
     * 减少库存操作
     * @param ticketId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update Ticket t set t.count = t.count - 1 where t.id = ?1")
    int reduceCount(Integer ticketId);


}
