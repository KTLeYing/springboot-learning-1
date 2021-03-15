package com.mzl.redisuserlike.controller;

import com.mzl.redisuserlike.entity.UserLike;
import com.mzl.redisuserlike.service.LikeService;
import com.mzl.redisuserlike.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   LikeController
 * @Description: 点赞控制器
 * @Author: mzl
 * @CreateDate: 2020/12/15 22:09
 * @Version: 1.0
 */
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private RedisService redisService;

    /**
     * 点赞
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @RequestMapping("/saveLikeRedis")
    public String saveLikedRedis(String likedUserId, String likedPostId){
        //更新点赞记录
        redisService.saveLikedRedis(likedUserId, likedPostId);
        //被点赞的用户的点赞数加1
        redisService.incrementLikedCount(likedUserId);
        return "从redis中: 用户" + likedPostId + "点赞用户" + likedUserId +"成功";
    }

    /**
     * 取消点赞
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @RequestMapping("/unlikeFromRedis")
    public String unlikeFromRedis(String likedUserId, String likedPostId){
        //更新点赞记录
        redisService.unlikeFromRedis(likedUserId, likedPostId);
        //被点赞的用户的点赞数减1
        redisService.decrementLikedFromRedis(likedUserId);
        return "从redis中: 用户" + likedPostId + "取消点赞用户" + likedUserId +"成功";
    }

    /**
     * 删除一条点赞记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @RequestMapping("/")
    public String deleteLikedFromRedis(String likedUserId, String likedPostId){
        redisService.deleteLikedFromRedis(likedUserId, likedPostId);
        return "从redis中:" + "删除用户" +  likedPostId + "对用户" + likedUserId +  "的点赞记录" +"成功";
    }

    /**
     * 根据被点赞人的id查询某个用户点赞的列表（即查询都有谁给这个人点了赞）
     * @param likedUserId
     * @param curPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/getLikedListByLikedUserId")
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Integer curPage, Integer pageSize){
        Pageable pageable = PageRequest.of(curPage, pageSize);
        return likeService.getLikedListByLikedUserId(likedUserId, pageable);
    }

    /**
     * 查询这个人给谁点了赞的所有的点赞列表
     * @param likedPostId
     * @param curPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/getLikedLIstByLikedPostId")
    public Page<UserLike> getLikedLIstByLikedPostId(String likedPostId, Integer curPage, Integer pageSize){
        Pageable pageable = PageRequest.of(curPage, pageSize);
        return likeService.getLikedListByLikedPostId(likedPostId, pageable);
    }

    /**
     * 根据被点赞的id和点赞的id查询某一条点赞的详细信息数据
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @RequestMapping("/getByLikedUserIdAndLikedPostId")
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return likeService.getByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }



}
