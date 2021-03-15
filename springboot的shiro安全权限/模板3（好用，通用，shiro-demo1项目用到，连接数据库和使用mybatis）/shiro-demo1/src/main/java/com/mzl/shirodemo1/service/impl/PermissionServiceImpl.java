package com.mzl.shirodemo1.service.impl;

import com.mzl.shirodemo1.dao.PermissionDao;
import com.mzl.shirodemo1.entity.Permission;
import com.mzl.shirodemo1.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   PermissionServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:12
 * @Version: 1.0
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    @Override
    public List<Permission> getByUserId(int userId) {
        return permissionDao.getByUserId(userId);
    }
}
