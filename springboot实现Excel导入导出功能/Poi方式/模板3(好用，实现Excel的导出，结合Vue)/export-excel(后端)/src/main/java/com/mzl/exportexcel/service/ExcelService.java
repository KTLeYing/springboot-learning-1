package com.mzl.exportexcel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @InterfaceName :   ExcelService
 * @Description: Excel业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:08
 * @Version: 1.0
 */
public interface ExcelService {

  /**
   * 下载数据到Excel
   * @param request
   * @param response
   * @param ids
   */
  void downloadExcel(HttpServletRequest request, HttpServletResponse response, String ids) throws IOException;
}
