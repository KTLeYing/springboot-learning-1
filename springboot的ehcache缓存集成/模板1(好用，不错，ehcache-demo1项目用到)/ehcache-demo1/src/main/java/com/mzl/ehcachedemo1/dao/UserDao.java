package com.mzl.ehcachedemo1.dao;

import com.mzl.ehcachedemo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2020/11/20 18:22
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
