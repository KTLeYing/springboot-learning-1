package com.mzl.redisuserlike.service;

import com.mzl.redisuserlike.entity.LikedCountDTO;
import com.mzl.redisuserlike.entity.UserLike;

import java.util.List;

/**
 * @InterfaceName :   RedisService
 * @Description: redis业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/12/16 0:35
 * @Version: 1.0
 */
public interface RedisService {

    /**
     * 点赞，设置状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLikedRedis(String likedUserId, String likedPostId);

    /**
     * 取消点赞， 设置状态为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从redis中删除一条点赞的数据（即写入数据库的时候执行）
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    void decrementLikedFromRedis(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞详情信息数据(用于写入数据库)
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有用户的点赞数量(用于写入数据库)
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();




}
