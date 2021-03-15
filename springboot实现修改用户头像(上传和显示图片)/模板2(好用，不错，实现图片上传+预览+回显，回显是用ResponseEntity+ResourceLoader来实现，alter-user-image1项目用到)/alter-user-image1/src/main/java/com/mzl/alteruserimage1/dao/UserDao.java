package com.mzl.alteruserimage1.dao;

import com.mzl.alteruserimage1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/2/19 13:53
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 保存用户信息到数据库
     * @param user
     */
    void saveUser(User user);

    /**
     * 查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findUserById(int id);
}
