package com.nhimcoi.yuh.chopsticks.Model;

/**
 * Created by Nhím Còi on 10/7/2017.
 */

public class FoodModel {
    private String mamon;
    private String tenmon;
    private long giatien;
    private String hinhanh;

    public FoodModel() {
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
