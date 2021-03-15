package com.mzl.exportexcel.service.impl;

import com.mzl.exportexcel.dao.NewsDao;
import com.mzl.exportexcel.entity.News;
import com.mzl.exportexcel.service.ExcelService;
import com.mzl.exportexcel.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName :   ExcelServiceImpl
 * @Description: Excel业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:08
 * @Version: 1.0
 */
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private NewsDao newsDao;

    /**
     * 下载数据到Excel中
     * @param request
     * @param response
     * @param ids
     */
    @Override
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, String ids) throws IOException {
        List<News> newsList = null;
        if (StringUtils.isEmpty(ids)){  //如果ids为空，则查询所有的记录
            newsList = newsDao.findAll();
        }else {   //如果ids不为空，则查询ids对应的记录
            List<String> idsList = Arrays.asList(ids.split("\\-"));
            System.out.println("kkkkk");
            System.out.println(idsList);
            newsList = newsDao.findByIds(idsList);
        }

        System.out.println(newsList);

        //调用excel工具类创建一个表格，并把从数据库获取到的数据写到表格里面
        HSSFWorkbook writeListWeekExcel = ExcelUtil.writeListWeekExcel("测试案列", newsList, 6);

        //响应到客户端
        this.setResponseHeader(request, response, "测试案例.xls");
        OutputStream outputStream = response.getOutputStream();  //获取输出到浏览器的字节流对象
        writeListWeekExcel.write(outputStream);   //把表格输出下载到浏览器
        outputStream.flush();    //设置缓存
        if (outputStream != null){
            outputStream.close();
        }
    }

    /**
     * 设置Excel导出响应头
     * @param request
     * @param response
     * @param fileName
     */
    private void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try{
            String userAgent = request.getHeader("User-Agent");
            System.out.println(userAgent);
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")){
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  //通过网络输出
            }else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Pargam", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
