package com.tianwen.springcloud.scoreapi.entity.excel;

import com.tianwen.springcloud.scoreapi.entity.word.SimpleWord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kimchh on 11/15/2018.
 */
public class SimpleSheet {

    private static final int DEFAULT_SHEET_INDEX = 0;

    private Sheet sheet;

    public SimpleSheet() {

    }

    public SimpleSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public SimpleSheet(Workbook workbook) {
        this(workbook, DEFAULT_SHEET_INDEX);
    }

    public SimpleSheet(Workbook workbook, int sheetIndex) {
        this(workbook.getSheetAt(sheetIndex));
    }

    public SimpleSheet(Workbook workbook, String sheetName) {
        this(workbook.getSheet(sheetName));
    }

    public SimpleSheet(InputStream is) throws IOException, InvalidFormatException {
        this(WorkbookFactory.create(is), DEFAULT_SHEET_INDEX);
    }

    public SimpleSheet(InputStream is, int sheetIndex) throws IOException, InvalidFormatException {
        this(WorkbookFactory.create(is), sheetIndex);
    }

    public SimpleSheet(InputStream is, String sheetName) throws IOException, InvalidFormatException {
        this(WorkbookFactory.create(is), sheetName);
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public int getColumnCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public String getData(CellPos pos) {
        return getData(pos.getRowIndex(), pos.getColIndex());
    }
    
    public String getData(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return null;
        }

        return getCellValue(cell, cell.getCellType());
    }

    private String getCellValue(Cell cell, int cellType) {
        switch (cellType)
        {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue() + "";

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            case Cell.CELL_TYPE_FORMULA:
                //return cell.getCellFormula() + "";
                return getCellValue(cell, cell.getCachedFormulaResultType());

            case Cell.CELL_TYPE_BLANK:
                return "";

            case Cell.CELL_TYPE_ERROR:
                return null;

            default:
                return null;
        }
    }

    public SimpleSheet setData(CellPos pos, String data) {
        return setData(pos.getRowIndex(), pos.getColIndex(), data);
    }

    public SimpleSheet setData(CellPos pos, String data, short alignment) {
        return setData(pos.getRowIndex(), pos.getColIndex(), data, alignment);
    }

    public SimpleSheet setData(CellPos pos, int span, String data) {
        return setData(pos.getRowIndex(), pos.getColIndex(), span, data);
    }

    public SimpleSheet setData(CellPos pos, int span, String data, short alignment) {
        return setData(pos.getRowIndex(), pos.getColIndex(), span, data, alignment);
    }

    public SimpleSheet setData(int rowIndex, int colIndex, String data) {
        return setData(rowIndex, colIndex, data, CellStyle.ALIGN_CENTER);
    }

    public SimpleSheet setData(int rowIndex, int colIndex, String data, short alignment) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        cell.setCellValue(data);

        setCellAlign(new CellPos(rowIndex, colIndex), alignment);

        return this;
    }

    public SimpleSheet setData(int rowIndex, int colIndex, int span, String data) {
        setData(rowIndex, colIndex, data, CellStyle.ALIGN_CENTER);
        mergeCells(rowIndex, colIndex, span);

        return this;
    }

    public SimpleSheet setData(int rowIndex, int colIndex, int span, String data, short alignment) {
        setData(rowIndex, colIndex, data, alignment);
        mergeCells(rowIndex, colIndex, span);

        return this;
    }

    public SimpleSheet setCellSpan(CellPos pos, int colSpan) {
        mergeCells(pos.getRowIndex(), pos.getColIndex(), colSpan);

        return this;
    }

    public void mergeCells(int rowIndex, int colIndex, int colSpan) {
        if (colSpan <= 1) {
            return;
        }

        mergeCells(rowIndex, 1, colIndex, colSpan);
    }

    public void mergeCells(int rowIndex, int rowSpan, int colIndex, int colSpan) {
        if (rowSpan > 0 && colSpan > 0) {
            CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex + rowSpan-1, colIndex, colIndex + colSpan-1);
            sheet.addMergedRegion(region);
        }
    }

    public Cell getCell(CellPos pos) {
        return getCell(pos.getRowIndex(), pos.getColIndex());
    }

    public Cell getCell(int rowIndex, int colIndex) {
        return sheet.getRow(rowIndex).getCell(colIndex);
    }

    public SimpleSheet setColumnWidth(int colIndex, int width) {
        sheet.setColumnWidth(colIndex, width);
        return this;
    }

    public SimpleSheet setCellAlign(CellPos pos, short alignment) {
        Cell cell = getCell(pos);
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = sheet.getWorkbook().createCellStyle();
        }

        cellStyle.setAlignment(alignment);
        cell.setCellStyle(cellStyle);

        return this;
    }

    public SimpleSheet setCellAlignCenter(CellPos pos) {
        return setCellAlign(pos, CellStyle.ALIGN_CENTER);
    }

    public SimpleSheet setCellVerticalAlign(CellPos pos, short alignment) {
        Cell cell = getCell(pos);
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = sheet.getWorkbook().createCellStyle();
        }

        cellStyle.setVerticalAlignment(alignment);
        cell.setCellStyle(cellStyle);

        return this;
    }

    public SimpleSheet setCellVerticalAlignCenter(CellPos pos) {
        return setCellVerticalAlign(pos, CellStyle.VERTICAL_CENTER);
    }

    public SimpleSheet setCellBackgroundColor(CellPos pos, short colorIndex) {
        Cell cell = getCell(pos);
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = sheet.getWorkbook().createCellStyle();
        }

//        cellStyle.setFillForegroundColor(colorIndex);
//        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);

        return this;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public void write(OutputStream outputStream) throws IOException {
        sheet.getWorkbook().write(outputStream);
    }

    public String getCellName(int rowIndex, int colIndex) {
        return CellReference.convertNumToColString(colIndex) + (rowIndex + 1);
    }
}
