package com.mzl.shirodemo3.dao;

import com.mzl.shirodemo3.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName :   SysRoleDao
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/25 0:20
 * @Version: 1.0
 */
@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Long> {

    /**
     * 获取用户的所有角色
     * @param id
     * @return
     */
    @Query(name = "findSysRoleByUid", nativeQuery = true,
            value = "select * from role_t where id in (select rid from user_role_t where uid = ?1)")
    List<SysRole> findSysRoleByUid(Long id);
}
