package com.mzl.exportexcel.service.impl;

import com.mzl.exportexcel.dao.NewsDao;
import com.mzl.exportexcel.entity.News;
import com.mzl.exportexcel.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName :   NewsServiceImpl
 * @Description: 新闻业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:11
 * @Version: 1.0
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    /**
     * 分页查询新闻列表
     * @return
     */
    @Override
    public List<News> newsList() {
        return newsDao.newsList();
    }

}
