package com.mzl.ticketgrabbing.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @ClassName :   Result
 * @Description: 抢票结果
 * @Author: mzl
 * @CreateDate: 2021/1/11 20:43
 * @Version: 1.0
 */
@Entity
public class Result {
    private int id;
    private Integer ticketId;
    private Integer userId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ticket_id", nullable = true)
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id &&
                Objects.equals(ticketId, result.ticketId) &&
                Objects.equals(userId, result.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketId, userId);
    }
}
