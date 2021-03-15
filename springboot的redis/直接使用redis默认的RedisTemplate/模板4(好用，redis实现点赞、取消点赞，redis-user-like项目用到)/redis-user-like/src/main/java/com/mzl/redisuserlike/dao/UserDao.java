package com.mzl.redisuserlike.dao;

import com.mzl.redisuserlike.entity.UserInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName :   UserDao
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:13
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<UserInfo, Integer> {

    /**
     * 根据用户的id查询该用户在UserInfo表中是否存在记录
     * @param likedUserId
     * @return
     */
    UserInfo findByUserId(String likedUserId);
}
