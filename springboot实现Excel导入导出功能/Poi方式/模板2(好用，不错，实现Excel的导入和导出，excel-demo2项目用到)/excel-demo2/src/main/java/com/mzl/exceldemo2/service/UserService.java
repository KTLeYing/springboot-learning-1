package com.mzl.exceldemo2.service;

import com.mzl.exceldemo2.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:28
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 导出excel表格(文件格式为xls或xlsx)
     * @param response
     * @return
     */
    boolean exportExcel(HttpServletRequest request, HttpServletResponse response);

    /**
     * 导入表格
     * @param file
     * @return
     */
    boolean importExcel(MultipartFile file);

    /**
     * 查询所有的用户
     * @return
     */
    List<User> findAllUser();
}
