package com.mzl.mybatispagehelperdemo.dao;

import com.mzl.mybatispagehelperdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2020/11/6 20:38
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 分页查询用户
     * @return
     */
    List<User> findUserByPage();

    /**
     * 分页查询用户（有查询条件）
     * @param username
     * @param age
     * @return
     */
    List<User> findUserByPage1(String username, Integer age);
}
