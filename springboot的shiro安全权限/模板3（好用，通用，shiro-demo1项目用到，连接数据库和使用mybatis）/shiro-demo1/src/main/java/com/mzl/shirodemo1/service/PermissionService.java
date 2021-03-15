package com.mzl.shirodemo1.service;

import com.mzl.shirodemo1.entity.Permission;

import java.util.List;

/**
 * @InterfaceName :   PermissionService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:11
 * @Version: 1.0
 */
public interface PermissionService {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    List<Permission> getByUserId(int userId);
}
