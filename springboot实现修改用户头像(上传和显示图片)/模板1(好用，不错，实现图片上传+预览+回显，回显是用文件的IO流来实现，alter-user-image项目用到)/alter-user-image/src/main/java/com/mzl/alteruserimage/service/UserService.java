package com.mzl.alteruserimage.service;

import com.mzl.alteruserimage.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/2/19 13:52
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 上传文件处理
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file, User user);

    /**
     * 查询用户
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 回显图片头像
     * @param path
     * @param response
     */
    void viewImage(String path, HttpServletResponse response) throws IOException;
}
