package com.mzl.securitydemo7.mapper;

import com.mzl.securitydemo7.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   SysUserMapper
 * @Description: 用户映射器
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:06
 * @Version: 1.0
 */
@Mapper
public interface SysUserMapper {

    /**
     * 从数据库中获取用户信息
     * @param username
     * @return
     */
    @Select("select * from sys_user where name = #{name}")
    SysUser findByIName(String username);
}
