package com.mzl.shirodemo3.service;

import com.mzl.shirodemo3.entity.SysPermission;
import com.mzl.shirodemo3.entity.SysRole;
import com.mzl.shirodemo3.entity.User;

import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:36
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 获取用户(使用用户名从数据库中查找)
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 添加用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 获取用户的所有角色
     * @param id
     * @return
     */
    List<SysRole> findSysRoleByUid(Long id);

    /**
     * 查询每个角色的权限
     * @param id
     * @return
     */
    List<SysPermission> findSysPermissionByRid(Long id);
}
