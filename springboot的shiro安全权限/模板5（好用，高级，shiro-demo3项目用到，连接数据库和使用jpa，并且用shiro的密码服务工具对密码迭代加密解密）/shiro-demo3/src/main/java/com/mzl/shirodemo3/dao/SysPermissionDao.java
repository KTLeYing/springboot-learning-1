package com.mzl.shirodemo3.dao;

import com.mzl.shirodemo3.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName :   SysPermission
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/25 0:21
 * @Version: 1.0
 */
@Repository
public interface SysPermissionDao extends JpaRepository<SysPermission, Long> {

    /**
     * 查询每个角色的权限
     * @param id
     * @return
     */
    @Query(name = "findSysPermissionByRid", nativeQuery = true,
            value = "select * from permission_t where id in (select pid from role_permission_t where rid = ?1)")
    List<SysPermission> findSysPermissionByRid(Long id);
}
