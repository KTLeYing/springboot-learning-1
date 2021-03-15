package com.mzl.securitydemo5.service.impl;

import com.mzl.securitydemo5.entity.SysRole;
import com.mzl.securitydemo5.mapper.SysRoleMapper;
import com.mzl.securitydemo5.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   SysRoleServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:04
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     */
    @Override
    public SysRole findById(Integer roleId) {
        return roleMapper.findById(roleId);
    }

    /**
     * 根据角色名来查询角色id
     * @return
     */
    @Override
    public SysRole findByName(String roleName) {
        return roleMapper.findByName(roleName);
    }
}
