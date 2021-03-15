package com.mzl.exceldemo4.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.mzl.exceldemo4.entity.ExcelData;
import com.mzl.exceldemo4.entity.User;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ClassName :   ExcelUtil
 * @Description: excel工具类
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:25
 * @Version: 1.0
 */
@Slf4j
public class ExcelUtil {

    /**
     * 导出 Excel ：一个 sheet，带表头
     * @param response  HttpServletResponse
     *  映射实体类，Excel 模型
     */
    public static void exportExcel(HttpServletResponse response, ExcelData excelData) throws IOException {
        //把东西按流形式进行保存方便等下进行文件下载功能
        ServletOutputStream outputStream = (ServletOutputStream) getOutputStream(excelData.getFileName(), response);

        ExcelWriterBuilder write = EasyExcel.write(outputStream, User.class);
        //设置sheet名称
        write.sheet(excelData.getSheetName());
        //导出sheet
        write.sheet().doWrite(excelData.getData());
    }

    /**
     * 导出文件时为Writer生成OutputStream，用于浏览器下载导出excel
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //设置响应头
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName  + ".xlsx");
            return response.getOutputStream();
        } catch (IOException e) {
            e.getMessage();
            System.out.println("创建文件失败！");
            return null;
        }
    }


    /**
     * 导入读取 整个Excel(多个 sheet 需要 各个 sheet 字段相同)
     * @param excel    文件
     * 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static List importExcel(MultipartFile excel, User user) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (user != null) {
                sheet.setClazz(user.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 解析的 Excel 文件里面的数据
     * @param excel         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel, ExcelListener excelListener) {
        String filename = excel.getOriginalFilename();
        if (filename == null) {
            System.out.println("文件格式错误！");
            return null;
        }
        if (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx")) {
            System.out.println("文件格式错误！");
            return null;
        }
        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(inputStream, null, excelListener, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
