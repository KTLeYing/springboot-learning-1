package com.mzl.securitydemo7.service.impl;

import com.mzl.securitydemo7.entity.SysRole;
import com.mzl.securitydemo7.mapper.SysRoleMapper;
import com.mzl.securitydemo7.service.SysRoleService;
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
}
