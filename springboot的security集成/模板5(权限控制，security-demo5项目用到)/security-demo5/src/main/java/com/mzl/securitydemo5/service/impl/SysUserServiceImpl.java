package com.mzl.securitydemo5.service.impl;

import com.mzl.securitydemo5.entity.SysUser;
import com.mzl.securitydemo5.mapper.SysUserMapper;
import com.mzl.securitydemo5.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   SysUserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:02
 * @Version: 1.0
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 从数据库中获取用户信息
     * @param username
     * @return
     */
    @Override
    public SysUser findByName(String username) {
        return userMapper.findByIName(username);
    }
}
