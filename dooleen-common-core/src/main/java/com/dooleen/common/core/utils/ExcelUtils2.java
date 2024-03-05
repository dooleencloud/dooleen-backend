//package com.dooleen.common.core.utils;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class ExcelUtils2 {
//    /**
//     * 创建excel文档
//     *
//     * @param getters list中map的key数组集合
//     * @param headers excel的列名
//     */
//    public static Workbook createWorkBook(List list, String[] getters, String[] headers, Class clazz) {
//
//        List<Method> methods = getMethodsByStrs(getters, clazz);
//
//        // 创建.xlsx工作簿
//        Workbook wb = new XSSFWorkbook();
//        // 创建第一个sheet（页），并命名
//        Sheet sheet = wb.createSheet("sheet1");
//        // 手动设置列宽.第一个参数表示要为第几列设,第二个参数表示列的宽度,n为列高的像素数.
//
//        for (int i = 0; i < getters.length; i++) {
//            sheet.setColumnWidth((short) i, (short) (35.7 * 200));
//        }
//
//        // 创建第一行
//        Row header = sheet.createRow(0);
//
//        // 创建两种单元格格式
//        CellStyle cellStyle1 = wb.createCellStyle();
//        CellStyle cellStyle2 = wb.createCellStyle();
//
//        // 创建两种字体
//        Font font1 = wb.createFont(); // 标题字体
//        Font font2 = wb.createFont(); // 正文字体
//
//        // 标题加粗
//        font1.setBold(true);
//
//        // 设置两种单元格的样式
//        setCellStype(cellStyle1, font1);
//        setCellStype(cellStyle2, font2);
//
//        //设置header
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = header.createCell(i);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(cellStyle1);
//        }
//
//        //设置data
//        int headersNum = 1;
//        for (int i = 0; i < list.size(); i++) {
//            Row row = sheet.createRow(i + headersNum);
//            for (int j = 0; j < methods.size(); j++) {
//                try {
//                    Object invoke = methods.get(j).invoke(list.get(i));
//                    if (invoke != null) {
//                        row.createCell(j).setCellValue(invoke.toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return wb;
//    }
//
//    private static void setCellStype(CellStyle cellStyle, Font font) {
//        font.setFontHeightInPoints((short) 10);
//        font.setColor(IndexedColors.BLACK.getIndex());
//        cellStyle.setFont(font);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//    }
//
//    private static List<Method> getMethodsByStrs(String[] getters, Class clazz) {
//        List<Method> list = new ArrayList<>();
//        for (String getter : getters) {
//            try {
//                list.add(clazz.getDeclaredMethod(getter));
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
//}