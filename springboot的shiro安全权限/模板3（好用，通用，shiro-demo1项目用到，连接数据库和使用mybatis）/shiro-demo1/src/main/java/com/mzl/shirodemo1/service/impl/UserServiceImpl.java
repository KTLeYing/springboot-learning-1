package com.mzl.shirodemo1.service.impl;

import com.mzl.shirodemo1.dao.UserDao;
import com.mzl.shirodemo1.entity.User;
import com.mzl.shirodemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   UserServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:11
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    @Override
    public User getByUserName(String userName) {
        return userDao.getByUserName(userName);
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<User> listUsers(Map<String, Object> map) {
        return userDao.listUsers(map);
    }

    /**
     *  用id来查询用户
     * @param id
     * @return
     */
    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Integer deleteUser(int id) {
        return userDao.deleteUser(id);
    }
}
