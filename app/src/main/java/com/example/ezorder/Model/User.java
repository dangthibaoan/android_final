package com.example.ezorder.Model;

public class User {
    private int userID;
    private String userName;
    private String phone;
    private String accountName;
    private String accountPass;
    private int user_role;

    public User() {
    }

    public User(String accountName, String accountPass) {
        this.accountName = accountName;
        this.accountPass = accountPass;
    }

    public User(int userID) {
        this.userID = userID;
    }

    public User(int userID, String userName, String phone, String accountName, String accountPass, int user_role) {
        this.userID = userID;
        this.userName = userName;
        this.phone = phone;
        this.accountName = accountName;
        this.accountPass = accountPass;
        this.user_role = user_role;
    }

    public User(String userName, String phone, String accountName, String accountPass, int user_role) {
        this.userName = userName;
        this.phone = phone;
        this.accountName = accountName;
        this.accountPass = accountPass;
        this.user_role = user_role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPass() {
        return accountPass;
    }

    public void setAccountPass(String accountPass) {
        this.accountPass = accountPass;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }
}
