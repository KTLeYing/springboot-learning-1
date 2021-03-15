package com.mzl.exportexcel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzl.exportexcel.entity.News;
import com.mzl.exportexcel.result.Result;
import com.mzl.exportexcel.result.StatusCode;
import com.mzl.exportexcel.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   PageController
 * @Description: 新闻控制器
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:02
 * @Version: 1.0
 */
@RestController
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 分页查询新闻列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/newsList/{page}/{size}")
    public Result newsList(@PathVariable int page, @PathVariable int size){
        System.out.println(page);
        System.out.println(size);
        try{
            PageHelper.startPage(page, size);   //开启分页功能
            List<News> newsList = newsService.newsList();
            PageInfo<News> pageInfo =  new PageInfo<>(newsList);
            System.out.println(pageInfo);
            pageInfo.getList().forEach(
                    e -> {
                        System.out.println(e);
                    });
            return new Result(true, StatusCode.OK, "查询成功", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"程序或网络错误", null);
        }
    }

}
