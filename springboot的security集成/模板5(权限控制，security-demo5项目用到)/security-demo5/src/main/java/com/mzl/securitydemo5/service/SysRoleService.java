package com.mzl.securitydemo5.service;

import com.mzl.securitydemo5.entity.SysRole;

/**
 * @InterfaceName :   SysRoleService
 * @Description: 角色业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:03
 * @Version: 1.0
 */
public interface SysRoleService {

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     */
    SysRole findById(Integer roleId);

    /**
     *
     * 根据角色名来查询角色id
     * @return
     */
    SysRole findByName(String roleName);
}
