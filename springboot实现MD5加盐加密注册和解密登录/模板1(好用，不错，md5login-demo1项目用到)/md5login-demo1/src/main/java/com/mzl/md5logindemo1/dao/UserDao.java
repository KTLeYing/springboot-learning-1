package com.mzl.md5logindemo1.dao;

import com.mzl.md5logindemo1.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户到
 * @Author: mzl
 * @CreateDate: 2020/11/27 19:21
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 从数据库中查询该用户
     * @param username
     * @param pd
     * @return
     */
    User findUser(String username, String pd);

    /**
     * 添加用户到数据库
     * @param username
     * @param password
     * @param name
     * @return
     */
    int register(String username, String password, String name);

    /**
     * 用户激活
     * @param username
     * @return
     */
    int active(String username);

    /**
     * 查询用户名是否已经存在
     * @param username
     * @return
     */
    String existUser(String username);
}
