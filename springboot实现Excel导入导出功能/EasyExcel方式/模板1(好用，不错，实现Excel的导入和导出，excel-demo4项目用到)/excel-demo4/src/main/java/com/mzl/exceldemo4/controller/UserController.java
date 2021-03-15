package com.mzl.exceldemo4.controller;

import com.mzl.exceldemo4.entity.User;
import com.mzl.exceldemo4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:27
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 导出excel表格(文件格式为xls或xlsx)
     * @param response
     * @param
     * @return
     */
    @RequestMapping("exportExcel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response){
        boolean isOk = userService.exportExcel(request, response);
        if (isOk){
            return "导出成功";
        }else {
            return "导出失败";
        }
    }

    /**
     * 导入表格（文件格式为xls或xlsx）【注意：这里导入的表格要有标题，且首行一定是标题；文件格式为xls或xlsx】
     * @param
     * @return
     */
    @RequestMapping("/importExcel")
    public String importExcel(MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println("文件的原始路径：" + file.getName());
        if (StringUtils.isEmpty(fileName)){
            return "文件名不能为空";
        }else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")){
                boolean isOk = userService.importExcel(file);
                if (isOk){
                    return "导入成功";
                }else {
                    return "导入失败";
                }
            }
        }
        return "文件格式错误";
    }

    /**
     * 查询所有的用户
     * @return
     */
    @RequestMapping("/findAllUser")
    public ModelAndView findAllUser(ModelAndView modelAndView){
        List<User> userList = userService.findAllUser();
        modelAndView.setViewName("excel");
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

}
