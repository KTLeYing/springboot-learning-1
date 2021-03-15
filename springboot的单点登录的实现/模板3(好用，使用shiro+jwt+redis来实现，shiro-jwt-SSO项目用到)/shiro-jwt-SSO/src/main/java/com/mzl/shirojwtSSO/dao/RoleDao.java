package com.mzl.shirojwtSSO.dao;

import com.mzl.shirojwtSSO.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   RoleDao
 * @Description: 角色dao
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:39
 * @Version: 1.0
 */
@Mapper
public interface RoleDao {

    /**
     * 获取该用户的角色
     * @param username
     * @return
     */
    List<Role> getRoles(String username);
}
