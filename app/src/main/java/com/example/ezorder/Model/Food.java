package com.example.ezorder.Model;

public class Food {
    private int foodID;
    private String foodName;
    private double foodPrice;
    private String foodUnit;
    private byte[] foodImage;
    private boolean foodStatus;

    public Food() {
    }

    public Food(String foodName, double foodPrice, String foodUnit, byte[] foodImage, boolean foodStatus) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodUnit = foodUnit;
        this.foodImage = foodImage;
        this.foodStatus = foodStatus;
    }

    public Food(int foodID, String foodName, double foodPrice, String foodUnit, byte[] foodImage, boolean foodStatus) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodUnit = foodUnit;
        this.foodImage = foodImage;
        this.foodStatus = foodStatus;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public byte[] getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(byte[] foodImage) {
        this.foodImage = foodImage;
    }

    public boolean isFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(boolean foodStatus) {
        this.foodStatus = foodStatus;
    }
}
