package com.mzl.securitydemo4.service.impl;

import com.mzl.securitydemo4.entity.SysUserRole;
import com.mzl.securitydemo4.mapper.SysUserRoleMapper;
import com.mzl.securitydemo4.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   SysUserRoleService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:03
 * @Version: 1.0
 */
@Service
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 根据用户id查询用户角色
     * @param id
     * @return
     */
    @Override
    public List<SysUserRole> findByUserId(Integer id) {
        return userRoleMapper.findByUserId(id);
    }
}
