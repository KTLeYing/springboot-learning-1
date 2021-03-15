package com.mzl.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ImagesUtil {
  private static String FILEPATH = "D:/ui/images/";  //图片存储的文件夹（绝对路径）

  public static String saveImages2(MultipartFile file) throws Exception {
    File filePath = new File(FILEPATH);
    if (!filePath.exists()) {
      filePath.mkdirs();
    }
    System.out.println(FILEPATH+file.getOriginalFilename());
    System.out.println(file.getOriginalFilename());
    file.transferTo(new File(FILEPATH+file.getOriginalFilename()));  //把图片（文件）写入目标目录下
    return FILEPATH+file.getOriginalFilename();
  }

  public static String saveImages(String name, String path, InputStream fileInputStream) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String fileName = path + sdf.format(new Date()) + name;
    File file = new File(path + sdf.format(new Date()));
    if (!file.exists()) {
      file.mkdirs();
    }
    createFile(fileName, fileInputStream);
    return fileName;
  }

  public static void createFile(String fileName, InputStream fileInputStream) throws Exception {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(fileName);
      byte[] b = new byte[1024];
      int num;
      while ((num = fileInputStream.read(b)) > -1) {
        out.write(b, 0, num);
      }
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}
