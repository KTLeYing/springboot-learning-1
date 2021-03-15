package com.mzl.redisuserlike.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName :   UserInfo
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:04
 * @Version: 1.0
 */
@Entity
@Table(name = "user_info", schema = "test3", catalog = "")
public class UserInfo {
    private int id;
    private String userId;
    private String username;
    private Integer likedCount;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 100)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "liked_count", nullable = true)
    public Integer getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(Integer likedCount) {
        this.likedCount = likedCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id &&
                Objects.equals(userId, userInfo.userId) &&
                Objects.equals(username, userInfo.username) &&
                Objects.equals(likedCount, userInfo.likedCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username, likedCount);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", likedCount=" + likedCount +
                '}';
    }
}
