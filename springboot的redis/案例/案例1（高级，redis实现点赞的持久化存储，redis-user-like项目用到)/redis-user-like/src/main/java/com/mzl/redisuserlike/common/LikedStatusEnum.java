package com.mzl.redisuserlike.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @EnumName :   LikedStatusEnum1
 * @Description: 点赞状态枚举类
 * @Author: mzl
 * @CreateDate: 2020/12/16 0:52
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum LikedStatusEnum {

    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞");

    private Integer code;
    private String msg;

}
