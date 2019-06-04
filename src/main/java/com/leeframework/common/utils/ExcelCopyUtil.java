package com.leeframework.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel复制操作工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月4日 下午10:58:43
 */
public final class ExcelCopyUtil {

    /**
     * 复制单元格
     * @datetime 2018年6月4日 下午10:58:49
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void copyCell(Cell targetCell, Cell sourceCell, Workbook targetWork, Workbook sourceWork, Map styleMap) {
        // 处理单元格样式
        if (styleMap != null) {
            if (targetWork == sourceWork) {
                targetCell.setCellStyle(sourceCell.getCellStyle());
            } else {
                String stHashCode = "" + sourceCell.getCellStyle().hashCode();
                CellStyle targetCellStyle = (CellStyle) styleMap.get(stHashCode);
                if (targetCellStyle == null) {
                    targetCellStyle = targetWork.createCellStyle();
                    targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                    styleMap.put(stHashCode, targetCellStyle);
                }
                targetCell.setCellStyle(targetCellStyle);
            }
        }

        // 处理单元格内容
        switch (sourceCell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            targetCell.setCellValue(sourceCell.getRichStringCellValue());
            break;
        case Cell.CELL_TYPE_NUMERIC:
            targetCell.setCellValue(sourceCell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            targetCell.setCellType(Cell.CELL_TYPE_BLANK);
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            targetCell.setCellValue(sourceCell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            targetCell.setCellFormula(sourceCell.getCellFormula());
            break;
        default:
            break;
        }
    }

    /**
     * 复制一行
     * @datetime 2018年6月4日 下午11:00:09
     */
    @SuppressWarnings("rawtypes")
    public static void copyRow(Row targetRow, Row sourceRow, Workbook targetWork, Workbook sourceWork, Map styleMap) {
        // 设置行高
        targetRow.setHeight(sourceRow.getHeight());

        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = targetRow.getCell(i);

            if (sourceCell != null) {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }
                // 拷贝单元格，包括内容和样式
                copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);

            }
        }
    }

    /**
     * 复制一个sheet
     * @datetime 2018年6月4日 下午11:00:27
     */
    public static void copySheet(Sheet targetSheet, Sheet sourceSheet, Workbook targetWork, Workbook sourceWork) {
        copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true);
    }

    /**
     * 复制一个sheet
     * @date 2015-6-9 上午10:29:14
     * @param targetSheet 目标sheet
     * @param sourceSheet 被复制的sheet
     * @param targetWork 目标Workbook
     * @param sourceWork 被复制的Workbook
     * @param copyStyle 是否复制样式
     */
    @SuppressWarnings("rawtypes")
    public static void copySheet(Sheet targetSheet, Sheet sourceSheet, Workbook targetWork, Workbook sourceWork, boolean copyStyle) {
        // 复制源表中的行
        int maxColumnNum = 0;
        Map styleMap = (copyStyle) ? new HashMap() : null;

        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row targetRow = targetSheet.createRow(i);

            if (sourceRow != null) {
                copyRow(targetRow, sourceRow, targetWork, sourceWork, styleMap);
                if (sourceRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = sourceRow.getLastCellNum();
                }
            }
        }

        // 复制源表中的合并单元格
        mergerRegion(targetSheet, sourceSheet);

        // 设置目标sheet的列宽
        for (int i = 0; i <= maxColumnNum; i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
        }
    }

    /**
     * 复制原有sheet的合并单元格到新创建的sheet
     * @date 2015-6-9 上午10:32:41
     * @param targetSheet 目标sheet
     * @param sourceSheet 被复制的sheet
     */
    public static void mergerRegion(Sheet targetSheet, Sheet sourceSheet) {
        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
            CellRangeAddress newRange = new CellRangeAddress(oldRange.getFirstRow(), oldRange.getLastRow(), oldRange.getFirstColumn(),
                    oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }

    /**
     * 在指定的位置插入行,样式和startRow一样
     * @date 2015-6-9 下午01:58:55
     * @param sheet
     * @param startRow 起始位置
     * @param rows 插入的行数
     */
    public static void insertRow(Sheet sheet, int startRow, int rows) {
        sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows, true, false);
        for (int i = 0; i < rows; i++) {
            Row sourceRow = null;
            Row targetRow = null;
            Cell sourceCell = null;
            Cell targetCell = null;

            sourceRow = sheet.getRow(startRow);
            targetRow = sheet.createRow(++startRow);
            targetRow.setHeight(sourceRow.getHeight());

            // 复制上一行的样式
            for (int m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
                sourceCell = sourceRow.getCell(m);
                targetCell = targetRow.createCell(m);
                targetCell.setCellStyle(sourceCell.getCellStyle());
                targetCell.setCellType(sourceCell.getCellType());
            }
        }
    }

}
