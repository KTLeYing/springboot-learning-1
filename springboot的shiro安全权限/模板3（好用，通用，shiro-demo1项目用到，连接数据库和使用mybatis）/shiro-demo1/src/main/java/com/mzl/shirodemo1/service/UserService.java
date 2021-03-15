package com.mzl.shirodemo1.service;

import com.mzl.shirodemo1.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:11
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param primaryPrincipal
     * @return
     */
    User getByUserName(String primaryPrincipal);

    /**
     * 查询所有用户
     * @return
     */
    List<User> listUsers(Map<String, Object> map);

    /**
     *  用id来查询用户
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer deleteUser(int id);
}
