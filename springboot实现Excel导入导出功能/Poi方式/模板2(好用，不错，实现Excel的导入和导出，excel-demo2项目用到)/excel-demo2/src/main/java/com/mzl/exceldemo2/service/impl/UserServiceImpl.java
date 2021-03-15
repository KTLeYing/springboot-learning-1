package com.mzl.exceldemo2.service.impl;

import com.mzl.exceldemo2.dao.UserDao;
import com.mzl.exceldemo2.entity.ExcelData;
import com.mzl.exceldemo2.entity.User;
import com.mzl.exceldemo2.service.UserService;
import com.mzl.exceldemo2.util.ExcelUtil;
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
        List<String[]> dataList = new ArrayList<>();
        userList.forEach( e -> {
            String[] arrs = new String[4];  //根据数据库表来定数组长度
            arrs[0] = String.valueOf(e.getId());
            arrs[1] = String.valueOf(e.getUsername());
            arrs[2] = String.valueOf(e.getPassword());
            arrs[3] = String.valueOf(e.getStatus());
            dataList.add(arrs);
        });
        //excel的文件名
        String fileName = "user-excel-export.xls";
        //表头赋值
        String[] head = {"序列", "用户名", "密码", "状态"};
        ExcelData data = new ExcelData();
        data.setHead(head);
        data.setData(dataList);
        data.setFileName(fileName);
        //实现导出
        try{
            log.info("导出数据结束。。。。。。");
            ExcelUtil.exportExcel(request, response, data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("导出数据失败。。。。。。");
            return false;
        }
    }

    /**
     * 导入表格
     * @param file
     * @return
     */
    @Override
    public boolean importExcel(MultipartFile file) {
        log.info("导入数据开始。。。。。。");
        try{
            //开始解析导入的数据
            List<Object[]> dataList = ExcelUtil.importExcel(file);
            //遍历表格的数据
            for (int i = 0; i < dataList.size(); i++){
                System.out.println(dataList.get(i)[0]);
                System.out.println(dataList.get(i)[1]);
                System.out.println(dataList.get(i)[2]);
                System.out.println(dataList.get(i)[3]);
                //封装每一行数据成用户的信息
                User user = new User();
//                user.setId((Integer) dataList.get(i)[0]);  //自增id，不需要设置
                user.setUsername(String.valueOf(dataList.get(i)[1]));
                user.setPassword(String.valueOf(dataList.get(i)[2]));
                user.setStatus((Integer) dataList.get(i)[3]);
                //保存表格（用户）的数据到数据库
                userDao.addUser(user);
                System.out.println("user " + i + " 导入成功");
            }
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
