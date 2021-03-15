package com.mzl.securitydemo5.service;

import com.mzl.securitydemo5.entity.SysPermission;

import java.util.List;

/**
 * @InterfaceName :   SysPermissionService
 * @Description: 用户权限业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/14 15:05
 * @Version: 1.0
 */
public interface SysPermissionService {

    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    List<SysPermission> findByRoleId(Integer roleId);
}
