package com.mzl.shirodemo2.dao;

import com.mzl.shirodemo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2020/9/21 21:15
 * @Version: 1.0
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
