package com.mzl.securitydemo5.service.impl;

import com.mzl.securitydemo5.entity.SysPermission;
import com.mzl.securitydemo5.mapper.SysRolePermissionMapper;
import com.mzl.securitydemo5.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   SysRolePermissionServiceImpl
 * @Description: 角色-权限业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/11/14 17:52
 * @Version: 1.0
 */
@Transactional
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

}
