package com.mzl.redisuserlike.utils;

/**
 * @ClassName :   RedisUtils
 * @Description: redis的键key的工具类
 * @Author: mzl
 * @CreateDate: 2020/12/16 0:41
 * @Version: 1.0
 */
public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的用户id作为key。格式：2222222::3333333 <==> 被点赞的id :: 点赞的id
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }


}
