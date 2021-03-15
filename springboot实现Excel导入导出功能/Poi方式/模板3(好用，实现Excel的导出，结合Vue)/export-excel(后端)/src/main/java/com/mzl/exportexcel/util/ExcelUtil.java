package com.mzl.exportexcel.util;

import com.mzl.exportexcel.entity.News;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @ClassName :   ExcelUtil
 * @Description: Excel导出表格工具类
 * @Author: mzl
 * @CreateDate: 2021/1/4 11:26
 * @Version: 1.0
 */
public class ExcelUtil {

    public static HSSFWorkbook writeListWeekExcel(String titleName, List<News> dataList,
                                                  int cloumnCount) {
        HSSFWorkbook workbook = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
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
            HSSFSheet sheet = workbook.createSheet(titleName);
            // 设置列宽
            sheet.setColumnWidth(0, 2000);

            /**
             * 往Excel中写新数据
             */
            // 标题
            Row titleRow = sheet.createRow(0);

            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(titleName);

            //设置第一行数据（即列的标题来的）
            Row row0 = sheet.createRow(0);
            Cell cell0 = row0.createCell(0);
            cell0.setCellValue("id");

//            Cell cell1 = row0.createCell(1);
//            cell1.setCellValue("create_time");

            Cell cell2 = row0.createCell(1);
            cell2.setCellValue("source");

            Cell cell3 = row0.createCell(2);
            cell3.setCellValue("time");

            Cell cell4 = row0.createCell(3);
            cell4.setCellValue("url");

            Cell cell5 = row0.createCell(4);
            cell5.setCellValue("title");

            // 得到要插入的每一条记录，真正开始遍历数据，并写入表格中（一行行来处理）
            for (int i = 0; i < dataList.size(); i++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(i + 1);

                String id = dataList.get(i).getId() + "";
//                String createTime = DateUtil.format(dataList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                String source = dataList.get(i).getSource();
                String time = DateUtil.format(dataList.get(i).getTime(), "yyyy-MM-dd HH:mm:ss");
                String url = dataList.get(i).getUrl();
                String title = dataList.get(i).getTitle();
                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(id);

//                    Cell second = row.createCell(1);
//                    second.setCellValue(createTime);

                    Cell third = row.createCell(1);
                    third.setCellValue(source);

                    Cell fourth = row.createCell(2);
                    fourth.setCellValue(time);

                    Cell fiveth = row.createCell(3);
                    fiveth.setCellValue(url);

                    Cell sixth = row.createCell(4);
                    sixth.setCellValue(title);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
