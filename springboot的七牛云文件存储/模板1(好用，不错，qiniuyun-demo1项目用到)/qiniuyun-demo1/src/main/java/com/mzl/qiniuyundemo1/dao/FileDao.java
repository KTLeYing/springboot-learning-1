package com.mzl.qiniuyundemo1.dao;

import com.mzl.qiniuyundemo1.entity.MyFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   FileDao
 * @Description: 文件dao
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:30
 * @Version: 1.0
 */
@Mapper
public interface FileDao {

    /**
     * 把上传的文件写入数据库
     * @param myFile
     */
    void addFile(MyFile myFile);

    /**
     * 通过id查询图片并显示
     * @param id
     * @return
     */
    @Select("select * from file where id = #{id}")
    MyFile findById(Integer id);
}
