package com.nhimcoi.yuh.chopsticks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nhím Còi on 9/28/2017.
 */

public class BranchModel implements Parcelable{
    private String diachi;
    private double latitude;

    public BranchModel() {
    }

    private double longitude;
    private double range;

    protected BranchModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        range = in.readDouble();
    }

    public static final Creator<BranchModel> CREATOR = new Creator<BranchModel>() {
        @Override
        public BranchModel createFromParcel(Parcel in) {
            return new BranchModel(in);
        }

        @Override
        public BranchModel[] newArray(int size) {
            return new BranchModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(range);
    }
}
