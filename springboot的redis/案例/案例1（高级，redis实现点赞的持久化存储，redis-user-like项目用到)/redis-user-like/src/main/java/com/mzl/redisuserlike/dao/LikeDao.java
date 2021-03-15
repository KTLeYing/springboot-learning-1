package com.mzl.redisuserlike.dao;

import com.mzl.redisuserlike.entity.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName :   LikeDao
 * @Description: 点赞dao
 * @Author: mzl
 * @CreateDate: 2020/12/15 22:12
 * @Version: 1.0
 */
public interface LikeDao extends JpaRepository<UserLike, Integer> {

    /**
     * 根据被点赞人的id查询某个用户点赞的列表（即查询都有谁给这个人点了赞）
     * @param likedUserId
     * @param code
     * @param pageable
     * @return
     */
    Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, Integer code, Pageable pageable);

    /**
     * 根据点赞别人的id来查询点赞列表（即这个人都给谁点了赞）
     * @param likedPostId
     * @param code
     * @param pageable
     * @return
     */
    Page<UserLike> findByLikedPostIdAndStatus(String likedPostId, Integer code, Pageable pageable);

    /**
     * 通过被点赞的用户和点赞的用户的id来查询是否存在点赞的记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    UserLike findByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);

}
