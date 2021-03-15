package com.mzl.redisuserlike.service.impl;

import com.mzl.redisuserlike.dao.UserDao;
import com.mzl.redisuserlike.entity.LikedCountDTO;
import com.mzl.redisuserlike.entity.UserInfo;
import com.mzl.redisuserlike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:09
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户的id查询该用户在UserInfo表中是否存在记录
     * @param likedUserId
     * @return
     */
    @Override
    public UserInfo findByUserLikedId(String likedUserId) {
        return userDao.findByUserId(likedUserId);
    }

    /**
     * 将redis中的每个用户对应的点赞数量存入数据库
     * @param userInfo
     */
    @Override
    public void save(UserInfo userInfo) {
        userDao.save(userInfo);
    }

    /**
     * 查询所有用户的信息
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return userDao.findAll();
    }
}
