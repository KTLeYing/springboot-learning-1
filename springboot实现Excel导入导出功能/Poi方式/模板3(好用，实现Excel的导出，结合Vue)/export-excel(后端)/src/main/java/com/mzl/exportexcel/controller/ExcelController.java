package com.mzl.exportexcel.controller;

import com.mzl.exportexcel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   ExcelController
 * @Description: Excel控制器
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:01
 * @Version: 1.0
 */
@RestController
@CrossOrigin
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 下载数据到Excel
     * @param request
     * @param response
     * @param ids
     */
    @RequestMapping("/download")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, String ids) throws IOException {
        excelService.downloadExcel(request, response, ids);
    }



}
