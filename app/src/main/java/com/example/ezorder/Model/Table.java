package com.example.ezorder.Model;

public class Table {
    private String tableID;
    private int number;
    private int status;

    public Table() {
    }

    public Table(int number, int status) {
        this.number = number;
        this.status = status;
    }

    public Table(String tableID, int number, int status) {
        this.tableID = tableID;
        this.number = number;
        this.status = status;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
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
