package com.mzl.uploadprogressbardemo.controller;

import com.mzl.uploadprogressbardemo.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @ClassName :   UploadController
 * @Description: 上传文件的控制器
 * @Author: mzl
 * @CreateDate: 2021/1/5 22:00
 * @Version: 1.0
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    private static final String BASE_PATH = "D:/ui/images";

    /**
     * 跳转去上传文件的页面
     * @return
     */
    @RequestMapping("")
    public String toUploadPage(){
        return "index";
    }

    /**
     * 上传文件
     * @return
     */
    @PostMapping("/fileUpload")
    @ResponseBody
    public Result fileUpload(MultipartFile uploadFile){
        if (uploadFile.isEmpty()){
            return new Result(500, "请选择文件上传", null);
        }

        try{
            File file = new File(BASE_PATH, uploadFile.getOriginalFilename());  //创建文件，文件路径为：BASE_PATH/uploadFile.getOriginalFilename()
            System.out.println(uploadFile.getOriginalFilename());    //获取文件名，即文件名.后缀
            System.out.println(file.getParentFile().toString());  //获取符目录的路径，即D:/ui/images
            if (!file.getParentFile().exists()){   //父目录不存在则创建
                file.getParentFile().mkdirs();
            }
            uploadFile.transferTo(file);    //把上传的文件保存到到目标目录下
            return new Result(200, "文件上传成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(501, "文件上传失败", null);
        }

    }


}
