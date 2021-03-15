package com.mzl.qqcomment.dao;

import com.mzl.qqcomment.entity.ReplyInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName :   CommentRepository
 * @Description: 评论dao层
 * @Author: mzl
 * @CreateDate: 2021/1/10 20:59
 * @Version: 1.0
 */
public interface CommentRepository extends JpaRepository<ReplyInfo, Integer> {

    /**
     * 查询评论回复列表
     * @return
     */
    List<ReplyInfo> findByComIdOrderByUpdateTime(int comId);


}
