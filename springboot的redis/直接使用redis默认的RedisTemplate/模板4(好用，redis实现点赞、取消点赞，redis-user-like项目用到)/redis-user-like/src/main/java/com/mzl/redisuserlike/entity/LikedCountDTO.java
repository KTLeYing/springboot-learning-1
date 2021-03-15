package com.mzl.redisuserlike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   LikedCountDTO
 * @Description: 点赞数量的dto
 * @Author: mzl
 * @CreateDate: 2020/12/16 1:44
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedCountDTO {

    private String likedUserId;

    private Integer count;


}
