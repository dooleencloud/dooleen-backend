package com.dooleen.common.core.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
/**
 * @Author：dl
 * @Date :2019/10/29
 */
public class ExcelUtil {
    /**
     * Excel表格导出
     * @param response HttpServletResponse对象
     * @param excelData Excel表格的数据，封装为List<List<String>>
     * @param sheetName sheet的名字
     * @param fileName 导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @throws IOException 抛IO异常
     */
    public static void exportExcel(HttpServletResponse response,
                                   List<List<String>> excelData,
                                   String sheetName,
                                   String fileName,
                                   int columnWidth) throws IOException {
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);
        //写入List<List<String>>中的数据
        int rowIndex = 0;
        for(List<String> data : excelData){
            //创建一个row行，然后自增1
            HSSFRow row = sheet.createRow(rowIndex++);
            // 创建两种单元格格式
            CellStyle cellStyle1 = workbook.createCellStyle();
            CellStyle cellStyle2 = workbook.createCellStyle();

            // 创建两种字体
            Font font1 = workbook.createFont(); // 标题字体
            Font font2 = workbook.createFont(); // 正文字体

            // 标题加粗
            font1.setBold(true);

            // 设置两种单元格的样式
            setCellStype(cellStyle1, font1);
            setCellStype(cellStyle2, font2);
            //遍历添加本行数据
            for (int i = 0; i < data.size(); i++) {
                //创建一个单元格
                HSSFCell cell = row.createCell(i);
                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));
                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
                if(rowIndex == 1) {
                    cell.setCellStyle(cellStyle1);
                }
            }
        }
        //设置中文文件名与后缀
        String encodedFileName = URLEncoder.encode(fileName + ".xls","utf-8").replaceAll("\\+", "%20");
        // 清除buffer缓存
        response.reset();
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename="+encodedFileName+"");
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
    }
    private static void setCellStype(CellStyle cellStyle, Font font) {
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(font);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    public static void exportPoi(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        //设置中文文件名与后缀
        String encodedFileName = URLEncoder.encode(fileName + ".xls","utf-8").replaceAll("\\+", "%20");
        // 清除buffer缓存
        response.reset();
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename="+encodedFileName+"");
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
    }
}

