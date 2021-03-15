package com.mzl.shirojwt.dao;

import com.mzl.shirojwt.dto.UserDTO;
import com.mzl.shirojwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:46
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 查询某个用户是否存在(使用账号)
     * @param user
     * @return
     */
    User selectOne(User user);

    /**
     * 用户登录
     * @param user1
     * @return
     */
    User login(User user1);

    /**
     * 查询用户列表
     * @return
     */
    List<User> findAllUser();

    /**
     * 用户注册
     * @param user
     */
    void register(User user);
}
