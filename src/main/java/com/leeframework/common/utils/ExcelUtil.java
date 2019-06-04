package com.leeframework.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel处理工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月31日 下午9:40:11
 */
public class ExcelUtil {

    /**
     * 创建标准的单元格样式：黑色边框,居中对齐
     * @date 2015-6-4 下午09:57:20
     * @param verticelAlign 水平对齐方式
     */
    public static CellStyle createStandedCellStyle(Workbook wb, short verticelAlign) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        cellStyle.setVerticalAlignment(verticelAlign);// 居中
        return cellStyle;
    }

    /**
     * 将excel工作簿转换为流
     * @date 2015-6-3 上午11:06:22
     * @param wb Workbook
     */
    public static InputStream getInputStream(Workbook wb) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        os.flush();
        os.close();
        return is;
    }

    /**
     * 根据Excel元素获取对应的值
     * @param cell HSSFCell元素对象
     * @return 一个Object类型的value
     */
    public static Object getValueByCellType(Cell cell) {
        Object value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getStringCellValue();
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                break;
            }
        }
        return value;
    }

    /**
     * 合并单元格,合并后的单元格采用原有的样式
     * @date 2015-6-9 下午02:04:48
     * @param sheet 工作簿
     * @param firstRow 第一行
     * @param lastRow 最后一行
     * @param firstCol 第一列
     * @param lastCol 最后一列
     */
    public static void mergedRegion(Workbook wb, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellStyle style = sheet.getRow(firstRow).getCell(firstCol).getCellStyle();
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        sheet.getRow(firstRow).getCell(firstCol).setCellStyle(style);
    }

    /**
     * 合并单元格,合并已经赋好数据的工作簿
     * @date 2015-6-4 下午09:28:57
     * @param sheet 工作簿
     * @param firstRow 第一行
     * @param lastRow 最后一行
     * @param firstCol 第一列
     * @param lastCol 最后一列
     * @param align 合并后的对齐方式
     */
    public static void mergedRegion(Workbook wb, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, short align) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        sheet.getRow(firstRow).getCell(firstCol).setCellStyle(createStandedCellStyle(wb, align));
    }

    /**
     * 多列合并，合并已经赋好数据的工作簿<br>
     * 
     * <pre>
     *  合并的规则是：根据起始列进行合并,数据相同的合并,后边的列也按照起始列的合并方式合并
     * </pre>
     * 
     * @date 2015-6-4 下午10:12:12
     * @param wb 工作簿
     * @param sheetIndex sheet索引
     * @param startRowIndex 起始行
     * @param firstColIndex 起始列
     * @param lastColIndex 最后一列
     * @param align 对齐方式
     */
    public static Workbook mergedRegionOnCulumn(Workbook wb, int sheetIndex, int startRowIndex, int firstColIndex, int lastColIndex, short align) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        // 获取行数
        int rows = sheet.getPhysicalNumberOfRows();

        int firstRow = startRowIndex;
        String preValue = "";
        for (int i = startRowIndex; i < rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(firstColIndex);
            Object vTemp = getValueByCellType(cell);
            String value = vTemp == null ? "" : vTemp.toString();

            if (i == startRowIndex) {
                preValue = value;
                continue;
            }

            if (!preValue.equals(value)) {
                int lastRow = i - 1;
                for (int c = firstColIndex; c <= lastColIndex; c++) {
                    mergedRegion(wb, sheet, firstRow, lastRow, c, c, align);
                }
                firstRow = i;
                preValue = value;
            }

            if (i == rows - 1) {
                for (int c = firstColIndex; c <= lastColIndex; c++) {
                    mergedRegion(wb, sheet, firstRow, i, c, c, align);
                }
            }
        }
        return wb;
    }

    /**
     * 单列合并,合并的规则是：在该列中,相同的行进行合并，合并已经赋好数据的工作簿
     * @date 2015-6-4 下午05:56:31
     * @param wb 工作簿
     * @param sheetIndex sheet索引
     * @param startRowIndex 起始行
     * @param columnIndex 被合并列的索引
     * @param align 对齐方式
     */
    public static Workbook mergedRegionOnCulumn(Workbook wb, int sheetIndex, int startRowIndex, int columnIndex, short align) {
        return mergedRegionOnCulumn(wb, sheetIndex, startRowIndex, columnIndex, columnIndex, align);
    }

    /**
     * 根据一个Excel文件解析数据<br>
     * 仅支持2003版本
     * @param file Excel文件
     * @return List<Object[]> 一个Object[]代表一行数据
     * @throws Exception 解析出现错误跑出异常
     */
    public static List<Object[]> parseData(File file) throws Exception {
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hwb.getSheetAt(0);
        return parseData(sheet);
    }

    /**
     * 根据一个HSSFSheet解析数据。<br>
     * 该解析是解析标格式的excel,即:含有标题行,且每行数据都完整
     * @param sheet Sheet
     * @return List<Object[]> 一个Object[]代表一行数据
     */
    public static List<Object[]> parseData(Sheet sheet) throws Exception {
        List<Object[]> list = new ArrayList<Object[]>();
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows >= 1) {
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                Object[] o = new Object[cells];
                for (int c = 0; c < cells; c++) {
                    o[c] = getValueByCellType(row.getCell(c));
                }
                list.add(o);
            }
        }
        return list;
    }

    /**
     * 默认的打印设置,默认A4纸张,页边距:上(1),下(1),左(0.9),右(0.9)
     * @date 2015-6-11 下午01:48:41
     * @param sheet
     * @param landscape 打印方向:打印方向，true:横向，false:纵向
     * @param isHorizontally 打印居中方式:true:置打印页面为水平居中,false:设置打印页面为垂直居中,null：不设置
     */
    public static void printSet(Sheet sheet, Boolean landscape, Boolean isHorizontally) {
        PrintSetup ps = sheet.getPrintSetup();

        if (landscape != null) {
            ps.setLandscape(landscape);
        }
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); // 纸张

        sheet.setMargin(Sheet.BottomMargin, (short) 0.4); // 页边距（下）
        sheet.setMargin(Sheet.LeftMargin, (short) 0.4); // 页边距（左）
        sheet.setMargin(Sheet.RightMargin, (short) 0.4); // 页边距（右）
        sheet.setMargin(Sheet.TopMargin, (short) 0.4); // 页边距（上）

        ps.setHeaderMargin((short) 0.5);// 页眉
        ps.setFooterMargin((short) 0.5);// 页脚

        if (isHorizontally != null) {
            if (isHorizontally) {
                sheet.setHorizontallyCenter(true); // 设置打印页面为水平居中
            } else {
                sheet.setVerticallyCenter(true); // 设置打印页面为垂直居中
            }
        }
    }

}
