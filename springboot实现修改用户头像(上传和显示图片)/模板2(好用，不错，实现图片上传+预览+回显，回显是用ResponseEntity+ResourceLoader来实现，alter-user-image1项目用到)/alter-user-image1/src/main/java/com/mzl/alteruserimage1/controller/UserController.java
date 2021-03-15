package com.mzl.alteruserimage1.controller;

import com.mzl.alteruserimage1.entity.User;
import com.mzl.alteruserimage1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/2/19 13:51
 * @Version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final ResourceLoader resourceLoader;
    @Autowired
    public UserController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    /**
     * 跳转到用户信息页面
     * @return
     */
    @RequestMapping("/toUserInfo")
    public String toUserInfo(Model model){
        //假如查询id为1的用户
        User user = userService.findUserById(1);
        model.addAttribute("user", user);
        return "userInfo";
    }

    /**
     * 跳转到修改用户信息页面
     * @return
     */
    @RequestMapping("/toAlterInfo")
    public String toAlterInfo(Integer id, Model model){
        System.out.println(id);
        //假如查询id为1的用户
        User user1 = userService.findUserById(id);
        model.addAttribute("user", user1);
        return "alterInfo";
    }

    /**
     * 上传图片
     * @param file
     * @param model
     * @return
     */
    @RequestMapping("/imageUpload")
    public String imageUpload(@RequestParam("fileName") MultipartFile file, User user, Model model){
        //判断文件大小
        if (file.getSize() / 1000 > 1000){
            return "图片大小不能超过100KB";
        }
        //判断上传文件格式
        String fileType = file.getContentType();
        System.out.println("文件类型：" + fileType);
        if (fileType.equals("image/jpeg") && fileType.equals("image/png") && fileType.equals("image/jpg")){
            return "图片格式不正确";
        }
        //上传文件处理
        try{
            String path = userService.uploadImage(file, user);
            model.addAttribute("path", path);
            return "redirect:/toUserInfo";
        } catch (Exception e) {
            e.printStackTrace();
            return "图片格式不正确";
        }
    }

    /**
     * 回显图片头像
     * @param path
     * @return
     */
    @RequestMapping("/viewImage")
    public ResponseEntity viewImage(String path, HttpServletResponse response) throws IOException {
        System.out.println("iii");
        System.out.println(path);
        try{
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
