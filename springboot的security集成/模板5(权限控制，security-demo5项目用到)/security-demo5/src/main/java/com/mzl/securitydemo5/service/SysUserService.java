package com.mzl.securitydemo5.service;

import com.mzl.securitydemo5.entity.SysUser;

/**
 * @InterfaceName :   SysUserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:03
 * @Version: 1.0
 */
public interface SysUserService {

    /**
     * 从数据库中获取用户信息
     * @param username
     * @return
     */
    SysUser findByName(String username);
}
