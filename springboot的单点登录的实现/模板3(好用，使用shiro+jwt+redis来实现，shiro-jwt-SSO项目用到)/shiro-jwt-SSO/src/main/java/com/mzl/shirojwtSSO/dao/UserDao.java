package com.mzl.shirojwtSSO.dao;

import com.mzl.shirojwtSSO.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:36
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 查询用户
     * @param username
     * @return
     */
    User findUser(String username);
}
