package com.mzl.seckilldemo1.dao;

import com.mzl.seckilldemo1.dto.UserDTO;
import com.mzl.seckilldemo1.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/3/1 17:49
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 从数据库中获取用户
     * @param id
     * @return
     */
    User getById(long id);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int register(UserDTO user);
}
