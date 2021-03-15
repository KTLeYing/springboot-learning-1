package com.mzl.qqcomment.service;

import com.mzl.qqcomment.dto.ReplyDTO;

import java.util.List;

/**
 * @InterfaceName :   CommentService
 * @Description: 评论业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/1/10 20:57
 * @Version: 1.0
 */
public interface CommentService {

    List<ReplyDTO> getReplyList(int comId);

    /**
     * 删除某一条评论
     * @param id
     */
    void removeRely(int id);
}
