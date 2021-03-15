package com.mzl.securitydemo5.service.impl;

import com.mzl.securitydemo5.entity.SysPermission;
import com.mzl.securitydemo5.mapper.SysPermissionMapper;
import com.mzl.securitydemo5.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   SysPermissionServiceImpl
 * @Description: 权限
 * @Author: mzl
 * @CreateDate: 2020/11/14 15:05
 * @Version: 1.0
 */
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    /**
     * 根据角色名来查询角色id
     * @param roleId
     * @return
     */
    @Override
    public List<SysPermission> findByRoleId(Integer roleId) {
        return permissionMapper.findByRoleId(roleId);
    }
}
