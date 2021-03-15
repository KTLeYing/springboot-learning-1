package com.mzl.richtextarticle.service;

import com.github.pagehelper.PageInfo;
import com.mzl.richtextarticle.entity.Article;
import com.mzl.richtextarticle.entity.PageBean;

/**
 * @InterfaceName :   ArticleService
 * @Description: Article业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/1/6 10:39
 * @Version: 1.0
 */
public interface ArticleService {

    /**
     * 添加文章
     * @param article
     */
    int addArticle(Article article) throws Exception;

    /**
     * 分页显示新闻列表
     * @param pageBean
     * @return
     */
    PageInfo loadPage(PageBean pageBean) throws Exception;
}
