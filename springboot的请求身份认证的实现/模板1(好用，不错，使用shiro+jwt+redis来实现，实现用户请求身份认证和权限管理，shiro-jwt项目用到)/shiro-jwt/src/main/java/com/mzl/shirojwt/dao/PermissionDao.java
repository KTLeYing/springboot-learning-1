package com.mzl.shirojwt.dao;

import com.mzl.shirojwt.entity.Permission;
import com.mzl.shirojwt.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   PermissionDao
 * @Description: 权限dao
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:47
 * @Version: 1.0
 */
@Mapper
public interface PermissionDao {

    /**
     * 根据用户角色查询权限
     * @param role
     * @return
     */
    List<Permission> findPermissionByRole(Role role);
}
