package com.example.ezorder.Model;

import java.util.Date;

public class Order {
    private int orderID;
    private int orderNumber;
    private Date orderTime;
    private String orderNote;
    private int soLuong;
    private int order_Food_FK;
    private int order_Table_FK;

    public Order() {
    }

    public Order(int orderNumber, Date orderTime, String orderNote, int soLuong, int order_Food_FK, int order_Table_FK) {
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
        this.orderNote = orderNote;
        this.soLuong = soLuong;
        this.order_Food_FK = order_Food_FK;
        this.order_Table_FK = order_Table_FK;
    }

    public Order(int orderID, int orderNumber, Date orderTime, String orderNote, int soLuong, int order_Food_FK, int order_Table_FK) {
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
        this.orderNote = orderNote;
        this.soLuong = soLuong;
        this.order_Food_FK = order_Food_FK;
        this.order_Table_FK = order_Table_FK;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getOrder_Food_FK() {
        return order_Food_FK;
    }

    public void setOrder_Food_FK(int order_Food_FK) {
        this.order_Food_FK = order_Food_FK;
    }

    public int getOrder_Table_FK() {
        return order_Table_FK;
    }

    public void setOrder_Table_FK(int order_Table_FK) {
        this.order_Table_FK = order_Table_FK;
    }
}
