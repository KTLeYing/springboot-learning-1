package com.mzl.exportexcel.dao;

import com.mzl.exportexcel.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    /**
     * 分页查询新闻列表
     * @return
     */
    List<News> newsList();

    /**
     * 查询所有的记录
     * @return
     */
    @Select("select * from news")
    List<News> findAll();

    /**
     * 查询ids对应的记录
     * @param idsList
     * @return
     */
    List<News> findByIds(List<String> idsList);
}