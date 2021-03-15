package com.mzl.qiniuyundemo1.controller;

import com.mzl.qiniuyundemo1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   FileController
 * @Description: 文件控制器
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:29
 * @Version: 1.0
 */
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 跳转到上传文件的页面
     * @return
     */
    @RequestMapping("/toFile")
    public String toFile(){
        return "file";
    }

    /**
     * 上传文件（单个）
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file, Model model) throws Exception{
        if (!file.isEmpty()){
            try{
                String path = fileService.upload(file);
                model.addAttribute("code", 200);
                model.addAttribute("msg", "上传成功");
                model.addAttribute("data", path);
            } catch (Exception e) {
                model.addAttribute("code", 400);
                model.addAttribute("msg", "上传失败");
                e.printStackTrace();
            }
            return "uploadOneRes";
        }
        model.addAttribute("code", 201);
        model.addAttribute("msg", "上传的文件不能为空");
        return "uploadOneRes";
    }

    /**
     * 上传文件（多个）
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadMore")
    public String uploadMore(MultipartFile[] file, Model model) throws Exception{
        System.out.println(file.length);
        int emptyNum = 0;
        for(int i = 0; i < file.length; i++){
            if (file[i].isEmpty()){
                emptyNum++;
            }
        }
        if (emptyNum < file.length){
            try{
                List<String> path = fileService.uploadMore(file);
                model.addAttribute("code", 200);
                model.addAttribute("msg", "上传成功");
                model.addAttribute("data", path);
            } catch (Exception e) {
                model.addAttribute("code", 400);
                model.addAttribute("msg", "上传失败");
                e.printStackTrace();
            }
            return "uploadMoreRes";
        }
        model.addAttribute("code", 201);
        model.addAttribute("msg", "上传的文件不能为空");
        return "uploadMoreRes";
    }

    /**
     * 跳转到图片显示页面
     * @return
     */
    @RequestMapping("/toViewImage")
    public String toViewImage(){
        return "viewImage";
    }

    /**
     * 通过id查询图片并显示
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewImg")
    public String viewImg(Integer id, Model model){
        String path = fileService.findPathById(id);
        model.addAttribute("path", path);
        return "viewImage";
    }

}
