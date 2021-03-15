package com.mzl.shirodemo3.dao;

import com.mzl.shirodemo3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName :   UserDao
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:38
 * @Version: 1.0
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 获取用户(使用用户名从数据库中查找)
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
