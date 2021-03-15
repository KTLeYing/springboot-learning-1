package com.mzl.springbootdemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName :   User
 * @Description: 用户实体
 * @Author: 21989
 * @CreateDate: 2020/7/23 15:17
 * @Version: 1.0
 */
@Entity  //实体类（数据库表名）
//@Data   //使用lombok自动生成getter和setter方法
//@NoArgsConstructor //使用lombok自动生成无参的构造方法
public class User {

    @Id  //主键及生成策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String username;
    private String password;

    //可以使用lombok的@NoArgsConstructor来代替生成无参的构造方法
    public User() {
    }

    public User(int uid, String username, String password) {
        this.uid = uid;
        this.password = password;
        this.username = username;
    }

    //下面的setter和戈塔特人、方法可以使用lombok的@Data自动生成
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
