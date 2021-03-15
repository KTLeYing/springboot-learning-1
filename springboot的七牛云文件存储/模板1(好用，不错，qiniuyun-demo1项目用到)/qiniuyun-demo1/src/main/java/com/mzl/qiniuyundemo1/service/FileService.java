package com.mzl.qiniuyundemo1.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @InterfaceName :   FileService
 * @Description: 业务逻辑实现接口
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:31
 * @Version: 1.0
 */
public interface FileService {

    /**
     * 上传文件（单个）
     * @param file
     * @return
     * @throws Exception
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 上传文件（多个）
     * @param file
     * @return
     */
    List<String> uploadMore(MultipartFile[] file) throws Exception;

    /**
     * 通过id查询图片并显示
     * @param id
     * @return
     */
    String findPathById(Integer id);
}
