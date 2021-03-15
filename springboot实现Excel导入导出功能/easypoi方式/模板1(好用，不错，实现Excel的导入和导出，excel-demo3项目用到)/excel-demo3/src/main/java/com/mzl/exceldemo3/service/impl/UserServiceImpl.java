package com.mzl.exceldemo3.service.impl;

import com.mzl.exceldemo3.dao.UserDao;
import com.mzl.exceldemo3.entity.ExcelData;
import com.mzl.exceldemo3.entity.User;
import com.mzl.exceldemo3.service.UserService;
import com.mzl.exceldemo3.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   UserSrviceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:28
 * @Version: 1.0
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 导出excel表格(文件格式为xls或xlsx)
     * @param response
     * @return
     */
    @Override
    public boolean exportExcel(HttpServletRequest request, HttpServletResponse response) {
        log.info("导出数据开始。。。。。。");
        //查询数据并赋值给ExcelData
        List<User> userList = userDao.findAll();
        //excel的文件名（这里不需要后缀名）
        String fileName = "user-excel-export";
        //excel的标题
        String title = "用户信息";
        //sheet名称
        String sheetName = "工作表1";
        ExcelData data = new ExcelData();
        data.setData(userList);
        data.setFileName(fileName);
        data.setTitle(title);
        data.setSheetName(sheetName);
        //实现导出
        try{
            log.info("导出数据结束。。。。。。");
            ExcelUtil.exportExcel(response, User.class, data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("导出数据失败。。。。。。");
            return false;
        }
    }

    /**
     * 导入表格（普通导入）【注意：这里导入的表格要有标题，且首行一定是标题；文件格式为xls或xlsx】
     * @param file
     * @return
     */
    @Override
    public boolean importExcel(MultipartFile file) {
        log.info("导入数据开始。。。。。。");
        try{
            //开始解析导入的数据，普通导入【注意：这里导入的表格要有标题，且首行一定是标题；文件格式为xls或xlsx】
            List<User> userList = ExcelUtil.importExcel(file, User.class);
            userList.forEach(e -> {
                System.out.println(e);
                userDao.addUser(e);
            });
            log.info("导入数据结束。。。。。。");
            return true;
        } catch (Exception e) {
            log.info("导入数据失败。。。。。。");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAllUser() {
        return userDao.findAll();
    }


}
