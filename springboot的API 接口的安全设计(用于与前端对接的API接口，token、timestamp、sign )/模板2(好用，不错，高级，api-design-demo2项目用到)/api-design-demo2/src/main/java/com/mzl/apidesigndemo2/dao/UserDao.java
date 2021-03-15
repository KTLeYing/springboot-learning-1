package com.mzl.apidesigndemo2.dao;

import com.mzl.apidesigndemo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
