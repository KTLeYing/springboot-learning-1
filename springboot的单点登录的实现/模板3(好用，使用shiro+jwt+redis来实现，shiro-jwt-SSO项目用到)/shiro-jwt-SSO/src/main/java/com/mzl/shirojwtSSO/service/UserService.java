package com.mzl.shirojwtSSO.service;

import com.mzl.shirojwtSSO.entity.Permission;
import com.mzl.shirojwtSSO.entity.Role;
import com.mzl.shirojwtSSO.entity.User;

import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:40
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 查询用户是否存在数据库中
     * @param username
     * @return
     */
    User findUser(String username);

    /**
     * 获取该用户的角色
     * @param username
     * @return
     */
    List<Role> getRoles(String username);

    /**
     * 获取每个角色的权限
     * @param id
     * @return
     */
    List<Permission> getPermissions(Integer id);


}
