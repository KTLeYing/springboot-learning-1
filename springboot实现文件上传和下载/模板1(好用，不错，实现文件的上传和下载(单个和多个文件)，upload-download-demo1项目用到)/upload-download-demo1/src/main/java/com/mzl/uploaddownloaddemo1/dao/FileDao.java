package com.mzl.uploaddownloaddemo1.dao;

import com.mzl.uploaddownloaddemo1.entity.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   File
 * @Description: 文件dao
 * @Author: mzl
 * @CreateDate: 2021/2/5 23:41
 * @Version: 1.0
 */
@Mapper
public interface FileDao {

    /**
     * 把文件保存的路径写入数据库
     * @param
     */
    @Insert("insert into file(description, path) values(#{description}, #{path})")
    void savePath(File file);

    /**
     * 从数据库中通过id查询出要下载的文件路径
     * @param id
     * @return
     */
    @Select("select path from file where id = #{id}")
    String findFilePath(int id);
}
