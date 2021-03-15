package com.mzl.redisuserlike.service.impl;

import com.mzl.redisuserlike.common.LikedStatusEnum;
import com.mzl.redisuserlike.entity.LikedCountDTO;
import com.mzl.redisuserlike.entity.UserLike;
import com.mzl.redisuserlike.service.LikeService;
import com.mzl.redisuserlike.service.RedisService;
import com.mzl.redisuserlike.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @ClassName :   RedisServiceImpl
 * @Description: redis服务类
 * @Author: mzl
 * @CreateDate: 2020/12/16 0:37
 * @Version: 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LikeService likeService;

    /**
     * 点赞，设置状态为1
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void saveLikedRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    /**
     * 取消点赞， 设置状态为0
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    /**
     * 从redis中删除一条点赞的数据（即写入数据库的时候执行）
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, 1);
    }

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    @Override
    public void decrementLikedFromRedis(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }

    /**
     * 获取Redis中存储的所有点赞数据(用于写入数据库)
     * @return
     */
    @Override
    public List<UserLike> getLikedDataFromRedis() {
        //Cursor继承iterator
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()){    //遍历hash散列中的所有的键值对
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();   //获取键值对的key
            //分离出likedUserId和likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();  //获取键值对的值

            //封装成 UserLike 对象
            UserLike userLike = new UserLike();
            userLike.setLikedUserId(likedUserId);
            userLike.setLikedPostId(likedPostId);
            userLike.setStatus(value);
            list.add(userLike);

            //存入list后，从redis中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);

        }

        return list;
    }

    /**
     * 获取Redis中存储的所有用户的点赞数量(用于写入数据库)
     * @return
     */
    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> likedCountDTOList = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String) map.getKey();
            Integer count = (Integer) map.getValue();
            //将每个用户对应的点赞数量封装在 LikesCountDTO 对象中
            LikedCountDTO countDTO = new LikedCountDTO();
            countDTO.setLikedUserId(key);
            countDTO.setCount(count);
            likedCountDTOList.add(countDTO);

            //从redis删除这一条点赞数量的记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }

        return likedCountDTOList;
    }


}
