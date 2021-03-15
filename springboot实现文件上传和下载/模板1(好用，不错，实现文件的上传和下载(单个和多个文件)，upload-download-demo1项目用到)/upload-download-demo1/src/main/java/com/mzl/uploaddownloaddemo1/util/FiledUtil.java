package com.mzl.uploaddownloaddemo1.util;

import com.mzl.uploaddownloaddemo1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ClassName :   FiledUtil
 * @Description: 文件工具类（上传和下载）
 * @Author: mzl
 * @CreateDate: 2021/2/6 1:17
 * @Version: 1.0
 */
@Component
public class FiledUtil {

    @Autowired
    private FileService fileService;

    /**
     * 上传单个文件
     */
    public void uploadOne(MultipartFile file, String filePath, com.mzl.uploaddownloaddemo1.entity.File saveFile){
        try{
            //获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("文件名为：" + fileName);
            //获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println("文件的后缀名为：" + suffixName);

            // 设置文件存储路径
            String path = filePath + fileName + suffixName;
            //创建目标目录
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(dest);

            //把文件保存的路径写入数据库
            saveFile.setPath(path);  //设置文件保存的路径
            fileService.savePath(saveFile);  //文件写入数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param filePath
     */
    public void download(HttpServletResponse response, String filePath) throws IOException {
        //创建文件对象
        File file = new File(filePath);
        if (!file.exists()){  //文件找不到
            throw new FileNotFoundException();
        }

        String fileName = filePath.substring(filePath.lastIndexOf("//") + 2);  //获取并设置文件名
        System.out.println(fileName);
        FileInputStream fileInputStream = null;   //创建文件输入流对象
        BufferedInputStream bufferedInputStream = null;  //创建文件输入缓冲流对象
        //开始下载文件处理
        try{
            //响应头设置
            response.setContentType("application/force-download;charset=UTF-8");// 设置强制下载不打开
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];  //缓冲池，缓存读取到的数据
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream outputStream = response.getOutputStream();   //获响应的输出流对象（ServletOutputStream）
            int i = bufferedInputStream.read(buffer);  //第一次读取到的字节数
            while (i != -1){
                outputStream.write(buffer, 0, i);  //缓冲池满了，把缓存的字节输出到客户端浏览器
                outputStream.flush();  //一定要刷新输出流对象;
                i = bufferedInputStream.read(buffer);  //第二次读取，每次循环读取1024个字节，直到读取完成
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (bufferedInputStream != null){
                bufferedInputStream.close();
            }
            if (fileInputStream != null){
                fileInputStream.close();
            }
        }
    }


}
