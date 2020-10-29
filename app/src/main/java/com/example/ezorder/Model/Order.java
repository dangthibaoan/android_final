package com.example.ezorder.Model;

import java.util.Date;

public class Order {
    private int orderID;        // mã order gọi món
    private int orderNumber;    // số lượng món được gọi (vd 2 cốc nước)
    private Date orderTime;     // thời gian gọi món
    private String orderNote;   // ghi chú cho món
    private int userID;         // mã user của nhân viên gọi món
    private int status;         // trạng thái món ăn đã được chế biến (1) hay chưa (0)
    private int order_Food_FK;  // mã món ăn
    private String order_Table_FK; // mã bàn ăn được gọi món

    public Order() {
    }

    public Order(int orderID, int orderNumber, Date orderTime, String orderNote, int order_Food_FK, int userID, int status, String order_Table_FK) {
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
        this.orderNote = orderNote;
        this.order_Food_FK = order_Food_FK;
        this.userID = userID;
        this.status = status;
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

    public int getOrder_Food_FK() {
        return order_Food_FK;
    }

    public void setOrder_Food_FK(int order_Food_FK) {
        this.order_Food_FK = order_Food_FK;
    }

    public String getOrder_Table_FK() {
        return order_Table_FK;
    }

    public void setOrder_Table_FK(String order_Table_FK) {
        this.order_Table_FK = order_Table_FK;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
