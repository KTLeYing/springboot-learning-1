package com.mzl.richtextarticle.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzl.richtextarticle.entity.Article;
import com.mzl.richtextarticle.entity.PageBean;
import com.mzl.richtextarticle.result.Result;
import com.mzl.richtextarticle.result.StatusCode;
import com.mzl.richtextarticle.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * @ClassName :   ArticleController
 * @Description: 文章控制器
 * @Author: mzl
 * @CreateDate: 2021/1/6 10:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @param article
     * @return
     */
    @RequestMapping("/addArticle")
    public Result addArticle(Article article) throws Exception {
        System.out.println(article);
        int num = articleService.addArticle(article);
        if (num > 0){
            return new Result(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg(), null);
        }else {
            return new Result(StatusCode.SERVER_ERROR.getCode(), StatusCode.SERVER_ERROR.getMsg(), null);
        }
    }

    /**
     * 显示文章分页列表
     * @param pageBean
     * @return
     */
    @RequestMapping("/loadPage")
    public Result loadPage(PageBean pageBean){
        System.out.println(pageBean);
        try{
            PageInfo pageInfo = articleService.loadPage(pageBean);
            System.out.println(pageInfo);
            return new Result(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg(), pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(StatusCode.SERVER_ERROR.getCode(), StatusCode.SERVER_ERROR.getMsg(), null);
        }
    }



}
