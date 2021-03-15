package com.mzl.richtextarticle.dao;

import com.mzl.richtextarticle.entity.Article;
import com.mzl.richtextarticle.entity.PageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    /**
     * 分页显示新闻列表
     * @param pageBean
     * @return
     */
    List<Article> findByPage(PageBean pageBean);
}