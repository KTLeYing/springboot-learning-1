package com.mzl.qiniuyundemo1.service.impl;

import com.mzl.qiniuyundemo1.dao.FileDao;
import com.mzl.qiniuyundemo1.entity.MyFile;
import com.mzl.qiniuyundemo1.service.FileService;
import com.mzl.qiniuyundemo1.util.QiniuyunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   FileServiceImpl
 * @Description: 七牛云业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:31
 * @Version: 1.0
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private QiniuyunUtil qiniuyunUtil;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) throws Exception{
        String path = qiniuyunUtil.uploadImage(file);
        System.out.println("网上访问的路径：" + path);
        //把上传的文件写入数据库
        MyFile myFile = new MyFile();
        myFile.setDescription("七牛云上传文件");
        myFile.setPath(path);
        fileDao.addFile(myFile);
        return path;
    }

    /**
     * 上传文件（多个）
     * @param file
     * @return
     */
    @Override
    public List<String> uploadMore(MultipartFile[] file) throws IOException {
        List<String> pathList = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isEmpty()){  //文件不为空则处理
                String path = qiniuyunUtil.uploadImage(file[i]);
                System.out.println("网上访问的路径：" + path);
                //把上传的文件写入数据库
                MyFile myFile = new MyFile();
                myFile.setDescription("七牛云上传文件");
                myFile.setPath(path);
                fileDao.addFile(myFile);
                pathList.add(path);
            }
        }
        return pathList;
    }

    /**
     * 通过id查询图片并显示
     * @param id
     * @return
     */
    @Override
    public String findPathById(Integer id) {
        MyFile file = fileDao.findById(id);
        return file.getPath();
    }


}
