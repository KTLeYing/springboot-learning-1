package com.example.securitydemo1.mapper;

import com.example.securitydemo1.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   SysRoleMapper
 * @Description: 角色映射器
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:07
 * @Version: 1.0
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     */
    @Select("select * from sys_role where id = #{roleId}")
    SysRole findById(Integer roleId);
}
