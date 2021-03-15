package com.mzl.securitydemo7.service;

import com.mzl.securitydemo7.entity.SysUserRole;

import java.util.List;

/**
 * @InterfaceName :   SysUserRoleService
 * @Description: 用户角色业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:04
 * @Version: 1.0
 */
public interface SysUserRoleService {

    /**
     * 根据用户id查询用户角色
     * @param id
     * @return
     */
    List<SysUserRole> findByUserId(Integer id);

}
