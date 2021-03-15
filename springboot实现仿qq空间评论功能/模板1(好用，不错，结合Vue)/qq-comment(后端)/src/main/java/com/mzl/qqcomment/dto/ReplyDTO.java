package com.mzl.qqcomment.dto;

import com.mzl.qqcomment.entity.ReplyInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName :   CommentDTO
 * @Description: 回复dto
 * @Author: mzl
 * @CreateDate: 2021/1/10 21:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private int id;
    private Integer userId;
    private Integer comId;
    private String msg;
    private Timestamp updateTime;
    private String userName;
    private Integer replyId;
    //相当于自关联（针对2号人对于评论1的信息1，1号人回复了2号人信息2=》三个标准：用户id、评论id（评论id是指向回复id的）、回复id）
    private List<ReplyDTO> replyInfoList;

}
