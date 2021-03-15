package com.mzl.shirodemo1.dao;

import com.mzl.shirodemo1.entity.Permission;

import java.util.List;

/**
 * @InterfaceName :   PermissionDao
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:12
 * @Version: 1.0
 */
public interface PermissionDao {

    /**
     * 通过用户id查找用户
     * @param userId
     * @return
     */
    List<Permission> getByUserId(Integer userId);
}
