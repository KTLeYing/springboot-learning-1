package com.mzl.uploaddownloaddemo1.service;

import com.mzl.uploaddownloaddemo1.entity.File;

/**
 * @InterfaceName :   FileService
 * @Description: 文件业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/2/5 23:40
 * @Version: 1.0
 */
public interface FileService {

    /**
     * 把文件保存的路径写入数据库
     * @param
     */
    void savePath(File file);

    /**
     * 从数据库中通过id查询出要下载的文件路径
     * @param id
     * @return
     */
    String findFilePath(int id);
}
