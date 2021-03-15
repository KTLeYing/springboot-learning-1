package com.mzl.ticketgrabbing.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @ClassName :   Ticket
 * @Description: 票实体类
 * @Author: mzl
 * @CreateDate: 2021/1/11 20:43
 * @Version: 1.0
 */
@Entity
public class Ticket {
    private int id;
    private String name;
    private String content;
    private String username;
    private int count;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                count == ticket.count &&
                Objects.equals(name, ticket.name) &&
                Objects.equals(content, ticket.content) &&
                Objects.equals(username, ticket.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, username, count);
    }
}
