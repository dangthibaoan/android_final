package com.example.ezorder.Model;

public class Table {
    private int tableID;
    private int number;
    private int status;

    public Table() {
    }

    public Table(int number, int status) {
        this.number = number;
        this.status = status;
    }

    public Table(int tableID, int number, int status) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
