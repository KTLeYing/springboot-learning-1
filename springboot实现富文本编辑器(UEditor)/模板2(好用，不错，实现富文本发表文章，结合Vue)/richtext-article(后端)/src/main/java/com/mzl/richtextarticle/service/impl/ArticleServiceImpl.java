package com.mzl.richtextarticle.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzl.richtextarticle.dao.ArticleDao;
import com.mzl.richtextarticle.entity.Article;
import com.mzl.richtextarticle.entity.PageBean;
import com.mzl.richtextarticle.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @ClassName :   ArticleServiceImpl
 * @Description: Article业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2021/1/6 10:39
 * @Version: 1.0
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文章
     * @param article
     * @return
     */
    @Override
    public int addArticle(Article article) throws Exception {
        article.setCreateTime(new Timestamp(System.currentTimeMillis()));
        article.setUserId(1);
        return articleDao.insert(article);
    }

    /**
     * 分页显示新闻列表
     * @param pageBean
     * @return
     */
    @Override
    public PageInfo loadPage(PageBean pageBean) throws Exception {
        System.out.println(pageBean);
        System.out.println("kkk");
        PageHelper.startPage(pageBean.getPageIndex() == null? 1 : pageBean.getPageIndex(),
                pageBean.getPageSize() == null? 100 : pageBean.getPageSize());
//        PageHelper.startPage(1, 5);
        List<Article> articleLis = articleDao.findByPage(pageBean);
        System.out.println(articleLis);
        return new PageInfo<Article>(articleLis);
    }
}
