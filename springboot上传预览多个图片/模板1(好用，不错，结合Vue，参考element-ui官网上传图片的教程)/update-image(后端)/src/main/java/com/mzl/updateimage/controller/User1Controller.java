package com.mzl.updateimage.controller;

import com.mzl.updateimage.result.Result;
import com.mzl.updateimage.result.ResultCode;
import com.mzl.updateimage.service.User1Service;
import com.mzl.util.ImagesUtil;
import com.mzl.util.MD5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mzl.updateimage.entity.User1;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;

@RestController
@CrossOrigin
public class User1Controller {

    @Autowired
    private User1Service user1Service;

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    public Result login(String username, String password){
        try{
            Result result = user1Service.login(username, password);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(null);
        }
    }

    /**
     * 更新用户头像
     * @param user
     * @return
     */
    @PostMapping("/updateUser")
    public Result updateUser(User1 user){
        System.out.println(user);
        System.out.println(user.getImagePath());
        try{
            user1Service.updateUser(user);
            return Result.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(null);
        }
    }

    /**
     * 上传图片
     * @param file
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping("/uploadImages")
//    public Result uploadImages(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
//        try{
////            String realPath = request.getSession().getServletContext().getRealPath("/");
//            String s = ImagesUtil.saveImages2(file);
//            System.out.println(s);
//            return Result.success(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.fail(null);
//        }
//    }

    /**
     * 显示图片
     * getFeedBackPicture.do?picName=
     * @return
     */
//    @GetMapping("/image/{photoPath}")
//    public void getImage(HttpServletResponse response, @PathVariable("photoPath")String photoPath) throws Exception{
//        System.out.println("fff");
//        String realPath = "D:/ui/images/" + photoPath;
//        System.out.println(realPath);
//        FileInputStream inputStream = new FileInputStream(realPath);
//        int i = inputStream.available();  //获取文件有效的字节流
//        //byte数组用于存放图片字节数据
//        byte[] buff = new byte[i];  //文件字节流的存储缓存区
//        inputStream.read(buff);   //读取文件的字节流并存储到buff缓冲区中（用byte[]字节数组，一般与字节流连用，字节的缓冲区，用于存储文件的字节流）
//        //记得关闭输入流
//        inputStream.close();
//        //设置发送到客户端的响应内容类型
//        response.setContentType("image/*");
////        response.setContentType("image/jpeg;charset=utf-8");
//        response.setHeader("Content-Disposition", "inline; filename=girls.png");
//        OutputStream out = response.getOutputStream();
//        out.write(buff);    //把图片的字节流(图片)输出到页面
//        //关闭响应输出流
//        out.close();
//    }

    /**
     * 上传图片
     * @param req
     * @param multiReq
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/imgUpload")
    public Result imgUpload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
//        System.out.println("---" + fileUploadPath);//我这里用的springboot 在application.properties中配置，使用@Value 获取的文件上传目录
        String fileUploadPath = "D:/ui/images/";
        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String suffix = originalFilename.substring(originalFilename.indexOf("."));  //截取图片的后缀名(包括.)
        System.out.println(suffix);
        String localFileName = MD5Util.md5(file.getInputStream()) + suffix;
        System.out.println("uuu");
        System.out.println(localFileName);
        File localFile = new File(fileUploadPath + localFileName);
        if (!localFile.exists()) {
            localFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(localFile);
            FileInputStream fs = (FileInputStream) multiReq.getFile("file").getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fs.read(buffer)) != -1) {
                fos.write(buffer, 0, len);  //通过字节流来把图片写入保存到目标目录里（读取到一个字节就写入一次）
            }
            fos.close();
            fs.close();
        } else {
            System.out.println("文件已存在！！");
        }

        return Result.success(localFileName);//这里是我执行封装的返回结果，也可以使用map,
//        return Result.success("http://localhost:8081/img/" + localFileName);//这里是我执行封装的返回结果，也可以使用map,
    }



}