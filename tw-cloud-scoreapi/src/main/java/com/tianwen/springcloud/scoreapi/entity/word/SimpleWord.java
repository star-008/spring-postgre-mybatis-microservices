package com.tianwen.springcloud.scoreapi.entity.word;

import com.tianwen.springcloud.scoreapi.entity.excel.CellPos;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

public class SimpleWord {

    private XWPFDocument document = null;
    private int normalTextSize = 12;
    private int titleTextSize = 14;
    private float tableHeaderRatio = 1.2f;

    public SimpleWord () {
        document = new XWPFDocument();
    }

    public SimpleWord(FileInputStream fileInputStream) throws IOException {
        document = new XWPFDocument(fileInputStream);
    }

    public void addTitle(String title) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun runText = paragraph.createRun();

        paragraph.setAlignment(ParagraphAlignment.CENTER);
        runText.setBold(true);
        runText.setFontSize(titleTextSize);
        runText.setText("\n"+title);
    }

    public void addBreak() {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
    }

    public SimpleTable createTable(int rowCnt, int colCnt) {
        XWPFTable table = document.createTable(rowCnt, colCnt);
        return new SimpleTable(table);
    }

    public void write(OutputStream outputStream) throws IOException {
        document.write(outputStream);
    }

    public int getNormalTextSize() {
        return normalTextSize;
    }

    public void setNormalTextSize(int normalTextSize) {
        this.normalTextSize = normalTextSize;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public class SimpleTable {

        private CellPos cellPos = null;
        private XWPFTable table = null;

        public SimpleTable(XWPFTable xwpfTable) {
            cellPos = new CellPos();
            table = xwpfTable;
            table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(9300)); // to be fixed to fit on document width
        }

        public void setTableCellData(String data) {
            XWPFTableRow row = table.getRow(cellPos.getRowIndex());
            if (row == null)
                row = table.createRow();

            XWPFTableCell cell = row.getCell(cellPos.getColIndex());
            if (cell == null)
                cell = row.createCell();
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            for (XWPFParagraph p : cell.getParagraphs()) {
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = p.createRun();
                run.setFontSize(normalTextSize);
                run.setText(data);
            }
        }

        public void setTopHeader(List<String> headers) {
            if (!StringUtils.isEmpty(table.getRow(0).getCell(0).getText())) return;

            for (int i = 0; i < headers.size(); i++) {
                setTableCellData(headers.get(i));
                nextColumn();
            }

            nextRow();

            XWPFTableRow headerRow = table.getRow(0);
            headerRow.setHeight((int) (headerRow.getHeight()*tableHeaderRatio));
        }

        public void setRowData(List<String> rowDatas) {
            for (String data : rowDatas) {
                setTableCellData(data);
                nextColumn();
            }
            nextRow();
        }

        public void nextColumn() {
            cellPos.increaseColumn();
        }

        public void nextRow() {
            cellPos.increaseRow();
            cellPos.setColIndex(0);
        }
    }

}
