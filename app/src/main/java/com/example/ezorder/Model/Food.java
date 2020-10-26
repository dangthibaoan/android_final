package com.example.ezorder.Model;

public class Food {
    private int foodID;
    private String foodName;
    private int foodPrice;
    private String foodImage;
    private String foodUnit;
    private int foodStatus;

    public Food() {
    }

    public Food(int foodID) {
        this.foodID = foodID;
    }

    public Food(String foodName, int foodPrice, String foodImage, String foodUnit, int foodStatus) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodUnit = foodUnit;
        this.foodStatus = foodStatus;
    }

    public Food(int foodID, String foodName, int foodPrice, String foodImage, String foodUnit, int foodStatus) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodUnit = foodUnit;
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

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public int getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(int foodStatus) {
        this.foodStatus = foodStatus;
    }

}
