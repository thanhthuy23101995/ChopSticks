package com.nhimcoi.yuh.chopsticks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Nhím Còi on 9/23/2017.
 */

public class CommentModel implements Parcelable{
    private double chamdiem,luotthich;
    MemberModel memberModel;
    private String noidung, tieude, mauser;
    private List<String> imagesComentList;

    protected CommentModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readDouble();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        imagesComentList = in.createStringArrayList();
        id_comments = in.readString();
        memberModel = in.readParcelable(MemberModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getId_comments() {
        return id_comments;
    }

    public void setId_comments(String id_comments) {
        this.id_comments = id_comments;
    }

    private String id_comments;
    public List<String> getImagesComentList() {
        return imagesComentList;
    }

    public void setImagesComentList(List<String> imagesComentList) {
        this.imagesComentList = imagesComentList;
    }

    public CommentModel() {
    }

    public CommentModel(double chamdiem, double luotthich, MemberModel memberModel, String noidung, String tieude, String mauser) {
        this.chamdiem = chamdiem;
        this.luotthich = luotthich;
        this.memberModel = memberModel;
        this.noidung = noidung;
        this.tieude = tieude;
        this.mauser = mauser;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public double getChamdiem() {

        return chamdiem;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public double getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(chamdiem);
        parcel.writeDouble(luotthich);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeStringList(imagesComentList);
        parcel.writeString(id_comments);
        parcel.writeParcelable(memberModel,i);
    }
}
