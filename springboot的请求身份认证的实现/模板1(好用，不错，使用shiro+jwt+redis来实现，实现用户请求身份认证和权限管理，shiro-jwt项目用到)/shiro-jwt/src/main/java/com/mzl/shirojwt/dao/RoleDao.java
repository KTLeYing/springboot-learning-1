package com.mzl.shirojwt.dao;

import com.mzl.shirojwt.dto.RoleDTO;
import com.mzl.shirojwt.dto.UserDTO;
import com.mzl.shirojwt.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   RoleDao
 * @Description: 角色dao
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:47
 * @Version: 1.0
 */
@Mapper
public interface RoleDao {

    /**
     * 通过账号获取到该用户的角色信息
     * @param userDTO
     * @return
     */
    List<Role> findRoleListByAccount(UserDTO userDTO);
}
