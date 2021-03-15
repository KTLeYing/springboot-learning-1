package com.mzl.qiniuyundemo1.util;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName :   QiniuyunUtil
 * @Description: 七牛云工具类
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:32
 * @Version: 1.0
 */
@Component
@Slf4j
public class QiniuyunUtil {

    //七牛云的公钥
    @Value("${qiniu.accessKey}")
    private String accessKey;

    //七牛云的私钥
    @Value("${qiniu.secretKey}")
    private String secretKey;

    //七牛云的存储的空间名
    @Value("${qiniu.bucketName}")
    private String bucketName;

    //七牛云域名
    @Value("${qiniu.domain}")
    private String domain;


    /**
     * 七牛云图片上传(方法1，好用)
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) throws IOException {
        System.out.println("执行了七牛云文件上传...");
        System.out.println(accessKey);
        System.out.println(secretKey);
        System.out.println(bucketName);
        System.out.println(domain);
        //七牛文件上传管理器
        UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone2()));
        // 七牛认证管理
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        try {
            // 获取文件的名称
            String ImageName = file.getOriginalFilename();
            // 使用工具类根据上传文件生成唯一图片名称
            String imgName = getRandomImgName(ImageName);
            System.out.println("文件的名称" + imgName);
            //转换文件流类型
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            // 上传图片文件
            Response res = uploadManager.put(inputStream, imgName, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛云出错：" + res.toString());
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
            String path = domain + "/" + putRet.key;
            System.out.println("外链地址:" + path);
            System.out.println("lll");
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 使用工具类根据上传文件生成唯一图片名称
     * @param fileName
     * @return
     */
    private String getRandomImgName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (StringUtils.isEmpty(fileName) || index == -1){
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index);
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", ",");
        // 生成上传至云服务器的路径
        String path = DateUtil.today() + "-" + uuid + suffix;
        System.out.println(path);
        return path;
    }


    /**
     * 七牛云图片上传（方法2）
     * @param file
     * @return
     */
    public String uploadImage1(MultipartFile file) throws Exception {
        System.out.println("执行了七牛云文件上传...");
        System.out.println(accessKey);
        System.out.println(secretKey);
        System.out.println(bucketName);
        System.out.println(domain);
        //上传到七牛后保存的文件名
        String key;
        //上传文件的路径
        String FilePath;
        //密钥配置,认证信息
        Auth auth = Auth.create(accessKey, secretKey);
        //自动识别要上传的空间的初村区域是华东、华北、华南
        Zone z = Zone.autoZone();
        com.qiniu.storage.Configuration  c  = new com.qiniu.storage.Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        //获取upToken
        String upToken = auth.uploadToken(bucketName);
        //开始时间
        long  startTime = System.currentTimeMillis();
        String timeString = String.valueOf(startTime);
        log.info("______________开始时间:={}", startTime);
        Map<String ,String> map = new HashMap<String, String>();
        log.info("______________文件名:={}", file.getOriginalFilename());
        //获取当前路径
//        String uploadPath = request.getServletContext().getRealPath("resources/static");
        String uploadPath = "D:\\IDEA\\Idea-workplace\\qiniuyun-demo1\\src\\main\\resources\\static";
        System.out.println(uploadPath);
        log.info("______________路径:={}", uploadPath);

        String realPath = "";
        if (!file.isEmpty()){
            String fileName = timeString + file.getOriginalFilename();
            System.out.println(file.getOriginalFilename());
            System.out.println(fileName);
            try {
                String path = uploadPath + "\\" + fileName;
                System.out.println("kkk");
                System.out.println("path：" + path);
                file.transferTo(new File(path));
                key = fileName;
                FilePath = path;
                //上传到七牛
                //调用put方法
                try {
                    Response response = uploadManager.put(FilePath, key, upToken);
                    log.info("___________________response={}", response.bodyString());
                    realPath = domain + "/" + fileName;
                    System.out.println("hhh");
                    System.out.println("realPath:" + realPath);
                }catch (QiniuException e){
                    Response r = e.response;
                    log.error("上传七牛异常={}", r.toString());
                    throw e;
                }finally {
                    //上传七牛完成后删除本地文件
                    File deleteFile = new File(path);
                    if (deleteFile.exists()){
                        deleteFile.delete();
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
                throw e;
            }
        }
        //结束时间
        long endTime=System.currentTimeMillis();
        log.info("______________用时:={}",endTime-startTime+"ms");
        return realPath;
    }


}
