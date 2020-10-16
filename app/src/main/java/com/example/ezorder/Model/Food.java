package com.example.ezorder.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {
    private int foodID;
    private String foodName;
    private int foodPrice;
    private byte[] foodImage;
    private String foodUnit;
    private int foodStatus;

    public Food() {
    }

    public Food(int foodID) {
        this.foodID = foodID;
    }

    public Food(String foodName, int foodPrice, byte[] foodImage, String foodUnit, int foodStatus) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodUnit = foodUnit;
        this.foodStatus = foodStatus;
    }

    public Food(int foodID, String foodName, int foodPrice, byte[] foodImage, String foodUnit, int foodStatus) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodUnit = foodUnit;
        this.foodStatus = foodStatus;
    }

    protected Food(Parcel in) {
        foodID = in.readInt();
        foodName = in.readString();
        foodPrice = in.readInt();
        foodImage = in.createByteArray();
        foodUnit = in.readString();
        foodStatus = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    public byte[] getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(byte[] foodImage) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(foodID);
        dest.writeString(foodName);
        dest.writeInt(foodPrice);
        dest.writeByteArray(foodImage);
        dest.writeString(foodUnit);
        dest.writeInt(foodStatus);
    }
}
