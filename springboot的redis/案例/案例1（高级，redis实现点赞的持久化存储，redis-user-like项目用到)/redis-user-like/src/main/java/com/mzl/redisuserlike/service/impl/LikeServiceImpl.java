package com.mzl.redisuserlike.service.impl;

import com.mzl.redisuserlike.common.LikedStatusEnum;
import com.mzl.redisuserlike.dao.LikeDao;
import com.mzl.redisuserlike.entity.LikedCountDTO;
import com.mzl.redisuserlike.entity.UserInfo;
import com.mzl.redisuserlike.entity.UserLike;
import com.mzl.redisuserlike.service.LikeService;
import com.mzl.redisuserlike.service.RedisService;
import com.mzl.redisuserlike.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :   LikeServiceImpl
 * @Description: 点赞业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/12/15 22:11
 * @Version: 1.0
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDao likeDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    /**
     * 保存点赞的记录
     * @param userLike
     * @return
     */
    @Override
    public UserLike save(UserLike userLike) {
        return likeDao.save(userLike);
    }

    /**
     * 保存多个点赞记录
     * @param userLikeList
     * @return
     */
    @Override
    public List<UserLike> saveAll(List<UserLike> userLikeList) {
        return likeDao.saveAll(userLikeList);
    }

    /**
     * 根据被点赞人的id查询某个用户点赞的列表（即查询都有谁给这个人点了赞）
     * @param likedUserId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return likeDao.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    /**
     * 根据点赞别人的id来查询点赞列表（即这个人都给谁点了赞）
     * @param likedPostId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return likeDao.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    /**
     * 通过被点赞的用户和点赞的用户的id来查询是否存在点赞的记录
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return likeDao.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    /**
     * redis里面的点赞详情信息·数据存入数据库中
     */
    @Override
    public void saveLikedFromRedis() {
        List<UserLike> userLikeList = redisService.getLikedDataFromRedis();
        userLikeList.forEach(e -> {
            UserLike userLike = getByLikedUserIdAndLikedPostId(e.getLikedUserId(), e.getLikedPostId());
            if (userLike == null){   //如果点赞记录不在数据库中存在，直接存入
                save(e);
            }else {   //如果点赞记录在数据库中存在，则修改点赞记录的点赞状态
                userLike.setStatus(e.getStatus());
                save(userLike);
            }
        });
    }

    /**
     * 将redis中的每个用户对应的点赞数量存入数据库
     */
    @Override
    public void saveLikedCountFromRedis() {
        List<LikedCountDTO> likedCountDTOList = redisService.getLikedCountFromRedis();
        likedCountDTOList.forEach(e->{
            //根据用户的id查询该用户在UserInfo表中是否存在记录
            UserInfo userInfo = userService.findByUserLikedId(e.getLikedUserId());
            if (userInfo != null){  ////不为空，则更新, 新的点赞数=原来的点赞数+后来的点赞数
                userInfo.setLikedCount(userInfo.getLikedCount() + e.getCount());
                userService.save(userInfo);
            }
        });
    }


}
