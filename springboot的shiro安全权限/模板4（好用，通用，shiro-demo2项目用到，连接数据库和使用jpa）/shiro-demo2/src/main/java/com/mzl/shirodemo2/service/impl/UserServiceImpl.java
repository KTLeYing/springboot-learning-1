package com.mzl.shirodemo2.service.impl;

import com.mzl.shirodemo2.dao.UserDao;
import com.mzl.shirodemo2.entity.User;
import com.mzl.shirodemo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/9/21 21:15
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
