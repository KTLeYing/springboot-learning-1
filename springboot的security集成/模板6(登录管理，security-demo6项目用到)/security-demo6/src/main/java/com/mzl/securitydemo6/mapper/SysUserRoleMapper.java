package com.mzl.securitydemo6.mapper;

import com.mzl.securitydemo6.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName :   SysUserRoleMapper
 * @Description: 用户角色映射器
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:07
 * @Version: 1.0
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 根据用户id查询用户角色
     * @param id
     * @return
     */
    @Select("select * from sys_user_role where user_id = #{id}")
    List<SysUserRole> findByUserId(Integer id);

}
