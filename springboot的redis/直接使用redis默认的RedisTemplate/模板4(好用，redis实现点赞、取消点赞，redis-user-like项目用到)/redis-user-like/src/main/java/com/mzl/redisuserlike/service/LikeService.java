package com.mzl.redisuserlike.service;

import com.mzl.redisuserlike.entity.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @InterfaceName :   LikeService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/12/15 22:11
 * @Version: 1.0
 */
public interface LikeService {

    /**
     * 保存点赞的记录
     * @param userLike
     * @return
     */
    UserLike save(UserLike userLike);

    /**
     * 保存多个点赞记录
     * @param userLikeList
     * @return
     */
    List<UserLike> saveAll(List<UserLike> userLikeList);

    /**
     * 根据被点赞人的id查询某个用户点赞的列表（即查询都有谁给这个人点了赞）
     * @param likedUserId
     * @param pageable
     * @return
     */
    Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable);

    /**
     * 根据点赞别人的id来查询点赞列表（即这个人都给谁点了赞）
     * @param likedPostId
     * @param pageable
     * @return
     */
    Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable);

    /**
     * 通过被点赞的用户和点赞的用户的id来查询是否存在点赞的记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);

    /**
     * 将redis里面的点赞详情信息数据存入数据库中
     */
    void saveLikedFromRedis();

    /**
     * 将redis中的每个用户对应的点赞数量存入数据库
     */
    void saveLikedCountFromRedis();

}
