package com.mzl.securitydemo5.mapper;

import com.mzl.securitydemo5.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName :   SysPermissionMapper
 * @Description: 权限mapper
 * @Author: mzl
 * @CreateDate: 2020/11/14 15:14
 * @Version: 1.0
 */
@Mapper
public interface SysPermissionMapper {

    /**
     * 根据角色id查询所有的权限id
     * @param roleId
     * @return
     */
    @Select("select p.* from sys_role_permission rp, sys_permission p where p.id = rp.permission_id and role_id = #{roleId}")
    List<SysPermission> findByRoleId(Integer roleId);
}
