package com.mzl.ehcachedemo1.service;

import com.mzl.ehcachedemo1.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @InterfaceName :   UserService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/20 18:24
 * @Version: 1.0
 */
public interface UserService {

    /**
     * ####查询所有的用户(ehcache综合测试)#######
     * @return
     */
    List<User> findAllUser();

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    String addUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    String updateUser(User user);

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    String deleteUser(Long id);
}
