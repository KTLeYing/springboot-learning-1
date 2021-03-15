package com.mzl.qqcomment.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName :   ReplyInfo
 * @Description: 评论回复信息实体类
 * @Author: mzl
 * @CreateDate: 2021/1/10 21:04
 * @Version: 1.0
 */
@Entity
@Table(name = "reply_info", schema = "test2", catalog = "")
public class ReplyInfo {
    private int id;
    private Integer userId;
    private Integer comId;
    private String msg;
    private Timestamp updateTime;
    private String userName;
    private Integer replyId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "com_id", nullable = true)
    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    @Basic
    @Column(name = "msg", nullable = true, length = 255)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "reply_id", nullable = true)
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReplyInfo replyInfo = (ReplyInfo) o;
        return id == replyInfo.id &&
                Objects.equals(userId, replyInfo.userId) &&
                Objects.equals(comId, replyInfo.comId) &&
                Objects.equals(msg, replyInfo.msg) &&
                Objects.equals(updateTime, replyInfo.updateTime) &&
                Objects.equals(userName, replyInfo.userName) &&
                Objects.equals(replyId, replyInfo.replyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, comId, msg, updateTime, userName, replyId);
    }

    @Override
    public String toString() {
        return "ReplyInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", comId=" + comId +
                ", msg='" + msg + '\'' +
                ", updateTime=" + updateTime +
                ", userName='" + userName + '\'' +
                ", replyId=" + replyId +
                '}';
    }
}
