package com.mzl.mybatispagehelperdemo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzl.mybatispagehelperdemo.dao.UserDao;
import com.mzl.mybatispagehelperdemo.entity.User;
import com.mzl.mybatispagehelperdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑层实现类
 * @Author: mzl
 * @CreateDate: 2020/11/6 20:36
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 分页查询用户
     * @return
     */
    @Override
    public PageInfo<User> findUserByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);  //此句是核心，开启分页功能
        //查询内容数据
        List<User> userList = userDao.findUserByPage();
        //把数据存入pageInfo对象中
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        pageInfo.getList()
                .forEach(e -> {
                System.out.println(e);
            });
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());

        return pageInfo;
    }

    /**
     * 分页查询用户（有查询条件）
     * @param pageNum
     * @param pageSize
     * @param username
     * @param age
     * @return
     */
    @Override
    public PageInfo<User> findUserByPage1(Integer pageNum, Integer pageSize, String username, Integer age) {
        PageHelper.startPage(pageNum, pageSize);  //此句是核心，开启分页功能
        //查询内容数据
        List<User> userList = userDao.findUserByPage1(username, age);
        //把数据存入pageInfo对象中
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        pageInfo.getList()
                .forEach(e -> {
                    System.out.println(e);
                });
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());

        return pageInfo;
    }
}
