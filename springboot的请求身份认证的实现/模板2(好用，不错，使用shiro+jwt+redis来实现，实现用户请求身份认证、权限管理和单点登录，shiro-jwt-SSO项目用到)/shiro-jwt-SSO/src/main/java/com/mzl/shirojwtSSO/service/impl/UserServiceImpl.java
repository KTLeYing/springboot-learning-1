package com.mzl.shirojwtSSO.service.impl;

import com.mzl.shirojwtSSO.dao.PermissionDao;
import com.mzl.shirojwtSSO.dao.RoleDao;
import com.mzl.shirojwtSSO.dao.UserDao;
import com.mzl.shirojwtSSO.entity.Permission;
import com.mzl.shirojwtSSO.entity.Role;
import com.mzl.shirojwtSSO.entity.User;
import com.mzl.shirojwtSSO.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:40
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询用户
     * @param username
     * @return
     */
    @Override
    public User findUser(String username) {
        return userDao.findUser(username);
    }

    /**
     * 获取该用户的角色
     * @param username
     * @return
     */
    @Override
    public List<Role> getRoles(String username) {
        return roleDao.getRoles(username);
    }

    /**
     * 获取每个角色的权限
     * @param id
     * @return
     */
    @Override
    public List<Permission> getPermissions(Integer id) {
        return permissionDao.getPermissions(id);
    }



}
