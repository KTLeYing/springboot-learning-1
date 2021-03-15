package com.mzl.redisuserlike.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName :   UserLike
 * @Description: 用户点赞实体类
 * @Author: mzl
 * @CreateDate: 2020/12/15 22:08
 * @Version: 1.0
 */
@Entity
@Table(name = "user_like", schema = "test3", catalog = "")
public class UserLike {
    private int id;
    private String likedUserId;   //被点赞的id
    private String likedPostId;   //被点赞的id
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "liked_user_id", nullable = true)
    public String getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(String likedUserId) {
        this.likedUserId = likedUserId;
    }

    @Basic
    @Column(name = "liked_post_id", nullable = true)
    public String getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(String likedPostId) {
        this.likedPostId = likedPostId;
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
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLike userLike = (UserLike) o;
        return id == userLike.id &&
                Objects.equals(likedUserId, userLike.likedUserId) &&
                Objects.equals(likedPostId, userLike.likedPostId) &&
                Objects.equals(status, userLike.status) &&
                Objects.equals(createTime, userLike.createTime) &&
                Objects.equals(updateTime, userLike.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, likedUserId, likedPostId, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "UserLike{" +
                "id=" + id +
                ", likedUserId='" + likedUserId + '\'' +
                ", likedPostId='" + likedPostId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
