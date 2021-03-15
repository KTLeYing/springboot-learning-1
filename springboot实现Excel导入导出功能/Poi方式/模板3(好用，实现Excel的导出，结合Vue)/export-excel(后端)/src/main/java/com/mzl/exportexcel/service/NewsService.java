package com.mzl.exportexcel.service;

import com.mzl.exportexcel.entity.News;

import java.util.List;

/**
 * @InterfaceName :   NewsService
 * @Description: T新闻业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:11
 * @Version: 1.0
 */
public interface NewsService {

    /**
     * 分页查询新闻列表
     * @return
     */
    List<News> newsList();
}
