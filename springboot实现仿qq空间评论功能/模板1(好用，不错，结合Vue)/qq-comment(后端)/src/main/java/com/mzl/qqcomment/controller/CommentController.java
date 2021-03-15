package com.mzl.qqcomment.controller;

import com.mzl.qqcomment.dto.ReplyDTO;
import com.mzl.qqcomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   CommentController
 * @Description: 评论控制器
 * @Author: mzl
 * @CreateDate: 2021/1/10 20:56
 * @Version: 1.0
 */
@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章列表
     * @param comId
     * @return
     */
    @GetMapping("/replyList/{comId}")
    public Map getReplyList(@PathVariable int comId){
        List<ReplyDTO> replyDTOList = commentService.getReplyList(comId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "200");
        resultMap.put("data", replyDTOList);
        return resultMap;
    }

    /**
     * 删除某一条评论
     * @param id
     * @return
     */
    @RequestMapping("/removeReply/{id}")
    public Map removeReply(@PathVariable int id){
        commentService.removeRely(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "200");
        return resultMap;
    }




}
