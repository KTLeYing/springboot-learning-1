package com.mzl.shirodemo1.dao;

import com.mzl.shirodemo1.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:12
 * @Version: 1.0
 */
public interface UserDao {

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    User getByUserName(String userName);

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
