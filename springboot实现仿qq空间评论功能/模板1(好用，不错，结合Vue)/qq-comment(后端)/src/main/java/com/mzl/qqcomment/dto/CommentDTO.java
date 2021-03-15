package com.mzl.qqcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   CommentDTO
 * @Description: 评论dto
 * @Author: mzl
 * @CreateDate: 2021/1/10 21:24
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer id;
    private Integer userId;
    private String userName;
    private String comMsg;
    private String updateTime;


}
