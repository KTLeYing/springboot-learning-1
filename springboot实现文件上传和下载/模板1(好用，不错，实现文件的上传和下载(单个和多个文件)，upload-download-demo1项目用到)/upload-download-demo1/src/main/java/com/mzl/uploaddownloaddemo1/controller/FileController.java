package com.mzl.uploaddownloaddemo1.controller;

import com.mzl.uploaddownloaddemo1.entity.File;
import com.mzl.uploaddownloaddemo1.service.FileService;
import com.mzl.uploaddownloaddemo1.util.FiledUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName :   FileController
 * @Description: 文件控制器（上传和下载）
 * @Author: mzl
 * @CreateDate: 2021/2/5 23:39
 * @Version: 1.0
 */
@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private FiledUtil filedUtil;

    /**
     * 跳转到上传的页面
     * @return
     */
    @RequestMapping("/toUpload")
    public String toUpload(){

        return "upload";
    }

    /**
     * 上传一个文件
     * @param file
     * @return
     */
    @RequestMapping("/uploadOne")
    @ResponseBody
    public String uploadOne(MultipartFile file){
        if (file == null){
            return "文件为空";
        }

        // 设置文件存储的基础路径
        String filePath = "D://Program Files//MyFilesPractice(own)//filetest//";
        try{
            //上传文件处理
            File saveFile = new File();   //创建保存文件的对象
            saveFile.setDescription("单文件上传");  //设置文件描述
            filedUtil.uploadOne(file, filePath, saveFile); //上传文件
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }

    }

    /**
     * 上传多个文件（其实质是遍历多个文件列表然后调用单个文件上传的工具类来实现）
     * @param request
     * @return
     */
    @RequestMapping("/uploadMore")
    @ResponseBody
    public String uploadMore(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        // 设置文件存储的基础路径
        String filePath = "D://Program Files//MyFilesPractice(own)//filetest//";
        try{
            //遍历处理文件（其实遍历调用单个文件处理即可，原理一样的）
            files.forEach(e -> {
                //上传文件处理
                File saveFile = new File();   //创建保存文件的对象
                saveFile.setDescription("多文件上传");  //设置文件描述
                filedUtil.uploadOne(e, filePath, saveFile); //上传文件
            });
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 下载文件
     * @param response
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public String download(HttpServletResponse response, Integer fileId){
        //从数据库中通过id查询出要下载的文件路径
        String filePath = fileService.findFilePath(fileId);
        System.out.println(filePath);

        try{
            //下载文件
            filedUtil.download(response, filePath);
            return "下载成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "下载失败";
        }

    }

}
