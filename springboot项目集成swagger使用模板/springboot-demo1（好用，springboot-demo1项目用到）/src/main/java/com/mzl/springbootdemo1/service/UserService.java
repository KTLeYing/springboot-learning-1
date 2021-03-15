package com.mzl.springbootdemo1.service;

import com.mzl.springbootdemo1.entity.User;

import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:27
 * @Version: 1.0
 */
public interface UserService {

    //查询所有用户
    List<User> findAllUser();

    //添加用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);

    //查询用户信息（用id）
    User findUserById(int uid);

    //查询用户（用户id查询多个用户）
    Iterable<User> findUser(String uids);

    //删除用户(用id)
    void deleteUserById(int uid);

    //删除用户（用user来映射id）
    void deleteUser(User user);
}
