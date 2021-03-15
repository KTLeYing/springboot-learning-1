package com.mzl.exceldemo2.util;

import com.mzl.exceldemo2.entity.ExcelData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.poi.ss.usermodel.CellType.*;


/**
 * @ClassName :   ExcelUtil
 * @Description: excel工具类
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:25
 * @Version: 1.0
 */
@Slf4j
public class ExcelUtil {

    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, ExcelData excelData) {
        HSSFWorkbook workbook = null;
        try {
            // 创建工作薄
            workbook = new HSSFWorkbook();

            // 创建标题格式
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            // 创建一个居中格式
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            // 字体
            HSSFFont titleFont = workbook.createFont();
            titleFont.setFontName("宋体");
            // 设置字体大小
            titleFont.setFontHeightInPoints((short) 14);
            titleFont.setBold(true);// 粗体显示
            titleStyle.setFont(titleFont);

            // 创建表头格式
            HSSFCellStyle headStyle = workbook.createCellStyle();
            // 创建一个居中格式
            headStyle.setAlignment(HorizontalAlignment.CENTER);
            // 字体
            HSSFFont headFont = workbook.createFont();
            headFont.setFontName("宋体");
            // 设置字体大小
            headFont.setFontHeightInPoints((short) 12);
            headFont.setBold(true);// 粗体显示
            headStyle.setFont(headFont);
            // 设置边框
            headStyle.setBorderBottom(BorderStyle.THIN); // 下边框
            headStyle.setBorderLeft(BorderStyle.THIN);// 左边框
            headStyle.setBorderTop(BorderStyle.THIN);// 上边框
            headStyle.setBorderRight(BorderStyle.THIN);// 右边框

            // 创建单元格格式，并设置值表头 设置表头居中
            HSSFCellStyle style = workbook.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HorizontalAlignment.CENTER);
            // 自动换行
            style.setWrapText(true);
            HSSFFont font = workbook.createFont();
            font.setFontName("宋体");
            // 设置字体大小
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
            // 设置边框
            style.setBorderBottom(BorderStyle.THIN); // 下边框
            style.setBorderLeft(BorderStyle.THIN);// 左边框
            style.setBorderTop(BorderStyle.THIN);// 上边框
            style.setBorderRight(BorderStyle.THIN);// 右边框

            // 创建工作表
            HSSFSheet sheet = workbook.createSheet(excelData.getFileName());
            // 设置列宽
            sheet.setColumnWidth(0, 2000);

            /**
             * 往Excel中写新数据
             */
            // 标题
            Row titleRow = sheet.createRow(0);

            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(excelData.getFileName());

            //设置第一行数据（即列的标题来的）
            Row row0 = sheet.createRow(0);
            for (int i = 0; i < excelData.getHead().length; i++){
                Cell cell0 = row0.createCell(i);
                cell0.setCellValue(excelData.getHead()[i]);
            }
            Cell cell0 = row0.createCell(0);
            cell0.setCellValue("id");


            // 得到要插入的每一条记录，真正开始遍历数据，并写入表格中（一行行来处理）
            AtomicInteger index = new AtomicInteger(1);  // 创建行的行数（第几行）
            excelData.getData().forEach(e -> {
                System.out.println("index:" + index);
                // 创建一行：从第二行开始，跳过属性列,所以从1开始
                Row row = sheet.createRow(index.get());
                // 在一行内循环,遍历并赋值每一列的单元格数据
                for (int i = 0; i < e.length; i++){
                    Cell cell = row.createCell(i);
                    cell.setCellValue(e[i]);
                }
                index.getAndIncrement();   //index自增1
            });

            //响应到客户端
            //设置excel导出的响应头
            setResponseHeader(request, response, excelData.getFileName());
            OutputStream outputStream = response.getOutputStream();  //获取输出到浏览器的字节流对象
            workbook.write(outputStream);   //把表格输出下载到浏览器（导出）
            outputStream.flush();    //设置缓存
            if (outputStream != null){
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Excel导出响应头
     * @param request
     * @param response
     * @param fileName
     */
    public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
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


    /**
     * 方法名：importExcel
     * 功能：导入excel
     */
    public static List<Object[]> importExcel(MultipartFile file) {
        log.info("导入解析开始，fileName:{}",file.getOriginalFilename());
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    //比较是哪一种数据类型的数据来的
                    if (cell.getCellType() == NUMERIC.getCode()) {
                        objects[index] = (int) cell.getNumericCellValue();
                    }
                    if (cell.getCellType() == STRING.getCode()) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (cell.getCellType() == BOOLEAN.getCode()) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (cell.getCellType() == ERROR.getCode()) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        }catch (Exception e){
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 测试导入表格
     */
//    @Test
//    public void importExcelTest() {
//        try {
//            String fileName = "f:/test.xlsx";
//            List<Object[]> list = importExcel(fileName);
//            for (int i = 0; i < list.size(); i++) {
//                User user = new User();
//                user.setId((Integer) list.get(i)[0]);
//                user.setUsername((String) list.get(i)[1]);
//                user.setPassword((String) list.get(i)[2]);
//                user.setStatus((Integer) list.get(i)[3]);
//                System.out.println(user.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}
