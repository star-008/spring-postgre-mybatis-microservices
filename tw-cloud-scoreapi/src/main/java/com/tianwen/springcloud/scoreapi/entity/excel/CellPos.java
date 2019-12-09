package com.tianwen.springcloud.scoreapi.entity.excel;

/**
 * Created by kimchh on 11/17/2018.
 */
public class CellPos {
    private int rowIndex;
    private int colIndex;

    public CellPos() {

    }

    public CellPos(CellPos cellPos) {
        this.rowIndex = cellPos.rowIndex;
        this.colIndex = cellPos.colIndex;
    }

    public CellPos(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public void increaseRow() {
        this.rowIndex ++;
    }

    public void increaseColumn() {
        increaseColumn(1);
    }

    public void increaseColumn(int offset) {
        this.colIndex += offset;
    }

    public void increaseRow(int offset) {
        this.rowIndex += offset;
    }

    public void setPos(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }
}
