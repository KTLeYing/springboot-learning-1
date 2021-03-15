package com.mzl.activeuserdemo1.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:31
 * @Version: 1.0
 */
@Entity
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Integer status;
    /**
     * 用UUID生成一段数字,发送到用户邮箱，当用户点击链接时,再做一个校验,
     * 如果用户传来的code跟我们发送的code一致，则更改状态为“1”来激活用户.
     */
    private String code;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 200)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(status, user.status) &&
                Objects.equals(code, user.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, status, code);
    }
}
