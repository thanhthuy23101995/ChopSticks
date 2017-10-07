package com.nhimcoi.yuh.chopsticks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nhím Còi on 9/23/2017.
 */

public class MemberModel implements Parcelable {
    private String hoten, hinhanh, mathanhvies;

    public MemberModel() {
    }

    protected MemberModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        mathanhvies = in.readString();
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel in) {
            return new MemberModel(in);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMathanhvies() {
        return mathanhvies;
    }

    public void setMathanhvies(String mathanhvies) {
        this.mathanhvies = mathanhvies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoten);
        parcel.writeString(hinhanh);
        parcel.writeString(mathanhvies);
    }
}
