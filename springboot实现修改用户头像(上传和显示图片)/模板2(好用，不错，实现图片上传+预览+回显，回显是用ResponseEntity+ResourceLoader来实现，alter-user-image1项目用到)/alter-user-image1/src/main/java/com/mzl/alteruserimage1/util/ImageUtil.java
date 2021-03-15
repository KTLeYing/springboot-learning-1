package com.mzl.alteruserimage1.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @ClassName :   ImageUtil
 * @Description: 图像上传工具类
 * @Author: mzl
 * @CreateDate: 2021/2/19 13:54
 * @Version: 1.0
 */
@Component
public class ImageUtil {

    @Value("${web.uploadPath}")
    private String uploadPath;

    /**
     * 上传图片
     * @param file
     * @return
     */
    public String upload(MultipartFile file){
        //要上传的目标文件存放的绝对路径,上传后保存的文件名(需要防止图片重名导致的文件覆盖)
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名：" + suffixName);
        //重新生成文件名
        fileName = UUID.randomUUID().toString().replace("-", "") + suffixName;
        System.out.println("fileName：" + fileName);
        String realPath = uploadPath + fileName;
        System.out.println("realPath：" + realPath);
        //判断父目录是否存在
        File dest = new File(realPath);
        System.out.println(dest.getParentFile().getPath());
        System.out.println(dest.getParentFile().getAbsolutePath());
        System.out.println(dest.getParentFile().toString());
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();  //不存在父目录则创建父目录
        }
        try{
            //保存文件到本地
            file.transferTo(dest);
            return realPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
