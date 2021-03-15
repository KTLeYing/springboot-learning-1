package com.mzl.activeuserdemo1.dao;

import com.mzl.activeuserdemo1.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:17
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 校验邮箱中的code激活账户
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 用户登录
     * @return
     */
    User findUserByUsernameAndPasswordAndStatus(String username, String password, int i);
}
