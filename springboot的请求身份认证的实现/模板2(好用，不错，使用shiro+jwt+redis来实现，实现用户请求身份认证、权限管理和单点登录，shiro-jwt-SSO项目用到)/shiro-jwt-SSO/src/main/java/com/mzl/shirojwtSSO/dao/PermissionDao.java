package com.mzl.shirojwtSSO.dao;

import com.mzl.shirojwtSSO.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName :   PermissionDao
 * @Description: 权限dao
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:37
 * @Version: 1.0
 */
@Mapper
public interface PermissionDao {

    /**
     * 获取每个角色的权限
     * @param id
     * @return
     */
    List<Permission> getPermissions(Integer id);
}
