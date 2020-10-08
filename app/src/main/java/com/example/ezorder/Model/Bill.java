package com.example.ezorder.Model;

import java.util.Date;

public class Bill {
    private int billID;
    private Date billTime;
    private double billTotal;
    private int bill_User_FK;
    private int bill_Order_FK;

    public Bill() {
    }

    public Bill(Date billTime, double billTotal, int bill_User_FK, int bill_Order_FK) {
        this.billTime = billTime;
        this.billTotal = billTotal;
        this.bill_User_FK = bill_User_FK;
        this.bill_Order_FK = bill_Order_FK;
    }

    public Bill(int billID, Date billTime, double billTotal, int bill_User_FK, int bill_Order_FK) {
        this.billID = billID;
        this.billTime = billTime;
        this.billTotal = billTotal;
        this.bill_User_FK = bill_User_FK;
        this.bill_Order_FK = bill_Order_FK;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public int getBill_User_FK() {
        return bill_User_FK;
    }

    public void setBill_User_FK(int bill_User_FK) {
        this.bill_User_FK = bill_User_FK;
    }

    public int getBill_Order_FK() {
        return bill_Order_FK;
    }

    public void setBill_Order_FK(int bill_Order_FK) {
        this.bill_Order_FK = bill_Order_FK;
    }
}
