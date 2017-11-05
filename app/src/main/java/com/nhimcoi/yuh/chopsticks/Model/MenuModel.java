package com.nhimcoi.yuh.chopsticks.Model;

import java.util.List;

/**
 * Created by Nhím Còi on 10/7/2017.
 */

public class MenuModel {
    private String mathucdon;
    private String tenthucdon;
    List<FoodModel> foodModelList;

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public List<FoodModel> getFoodModelList() {
        return foodModelList;
    }

    public void setFoodModelList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }
}
