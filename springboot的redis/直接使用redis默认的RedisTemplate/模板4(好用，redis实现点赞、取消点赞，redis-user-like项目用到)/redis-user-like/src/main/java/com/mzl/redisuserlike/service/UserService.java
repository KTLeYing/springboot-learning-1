package com.mzl.redisuserlike.service;

import com.mzl.redisuserlike.entity.LikedCountDTO;
import com.mzl.redisuserlike.entity.UserInfo;

import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:09
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 根据用户的id查询该用户在UserInfo表中是否存在记录
     * @param likedUserId
     * @return
     */
    UserInfo findByUserLikedId(String likedUserId);

    /**
     * 更新用户的点赞数量
     * @param userInfo
     */
    void save(UserInfo userInfo);

    /***
     * 查询所有用户的信息
     * @return
     */
    List<UserInfo> findAll();


}
