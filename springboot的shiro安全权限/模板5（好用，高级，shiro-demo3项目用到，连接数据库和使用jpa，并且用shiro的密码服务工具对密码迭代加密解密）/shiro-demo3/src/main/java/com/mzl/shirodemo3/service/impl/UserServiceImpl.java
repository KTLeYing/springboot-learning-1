package com.mzl.shirodemo3.service.impl;

import com.mzl.shirodemo3.dao.SysPermissionDao;
import com.mzl.shirodemo3.dao.SysRoleDao;
import com.mzl.shirodemo3.dao.UserDao;
import com.mzl.shirodemo3.entity.SysPermission;
import com.mzl.shirodemo3.entity.SysRole;
import com.mzl.shirodemo3.entity.User;
import com.mzl.shirodemo3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:37
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysPermissionDao sysPermissionDao;

    /**
     * 获取用户(使用用户名从数据库中查找)
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void saveUser(User user) {
        System.out.println(user);
        userDao.save(user);
    }

    /**
     * 获取用户的所有角色
     * @param id
     * @return
     */
    @Override
    public List<SysRole> findSysRoleByUid(Long id) {
        return sysRoleDao.findSysRoleByUid(id);
    }

    /**
     * 查询每个角色的权限
     * @param id
     * @return
     */
//    @Override
    public List<SysPermission> findSysPermissionByRid(Long id) {
        return sysPermissionDao.findSysPermissionByRid(id);
    }

}
