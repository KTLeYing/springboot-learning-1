package com.mzl.uploaddownloaddemo1.service.impl;

import com.mzl.uploaddownloaddemo1.dao.FileDao;
import com.mzl.uploaddownloaddemo1.entity.File;
import com.mzl.uploaddownloaddemo1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   FileServiceImpl
 * @Description: 文件业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/5 23:40
 * @Version: 1.0
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    /**
     * 把文件保存的路径写入数据库
     * @param
     */
    @Override
    public void savePath(File file) {
        fileDao.savePath(file);
    }

    /**
     * 从数据库中通过id查询出要下载的文件路径
     * @param id
     * @return
     */
    @Override
    public String findFilePath(int id) {
        return fileDao.findFilePath(id);
    }

}
