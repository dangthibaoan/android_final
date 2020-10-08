package com.example.ezorder.Model;

public class Table {
    private int tableID;
    private int number;
    private boolean status;

    public Table() {
    }

    public Table(int number, boolean status) {
        this.number = number;
        this.status = status;
    }

    public Table(int tableID, int number, boolean status) {
        this.tableID = tableID;
        this.number = number;
        this.status = status;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
