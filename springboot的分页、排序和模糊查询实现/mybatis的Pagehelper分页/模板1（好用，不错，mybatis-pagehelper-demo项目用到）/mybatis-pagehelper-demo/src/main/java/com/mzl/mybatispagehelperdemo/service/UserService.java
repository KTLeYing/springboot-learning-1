package com.mzl.mybatispagehelperdemo.service;

import com.github.pagehelper.PageInfo;
import com.mzl.mybatispagehelperdemo.entity.User;

/**
 * @InterfaceName :   UserService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/6 20:36
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 分页查询用户
     * @return
     */
    PageInfo<User> findUserByPage(Integer pageNum, Integer pageSize);

    /**
     * 分页查询用户（有查询条件）
     * @param pageNum
     * @param pageSize
     * @param username
     * @param age
     * @return
     */
    PageInfo<User> findUserByPage1(Integer pageNum, Integer pageSize, String username, Integer age);
}
