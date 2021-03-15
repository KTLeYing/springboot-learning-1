package com.mzl.authcssodemo.dao;

import com.mzl.authcssodemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:42
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
