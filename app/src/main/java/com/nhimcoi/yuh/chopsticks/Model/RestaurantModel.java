package com.nhimcoi.yuh.chopsticks.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhimcoi.yuh.chopsticks.Interface.PlacesInterfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhím Còi on 9/16/2017.
 */

public class RestaurantModel implements Parcelable{
    private boolean giaohang;
    private String giomocua, giodongcua, tenquanan, videogioithieu, id_quanan;
    List<String> tienich;
    List<String> hinhanhquanans;
    List<BranchModel> branchModelList;
    List<MenuModel> menuModelList;

    public List<MenuModel> getMenuModelList() {
        return menuModelList;
    }

    public void setMenuModelList(List<MenuModel> menuModelList) {
        this.menuModelList = menuModelList;
    }

    protected RestaurantModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giomocua = in.readString();
        giodongcua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        id_quanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanans = in.createStringArrayList();
        luotthich = in.readLong();
        branchModelList = new ArrayList<>();
        in.readTypedList(branchModelList,BranchModel.CREATOR);
        commentModelList = new ArrayList<>();
        in.readTypedList(commentModelList,CommentModel.CREATOR);
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }



    public List<BranchModel> getBranchModelList() {
        return branchModelList;
    }

    public void setBranchModelList(List<BranchModel> branchModelList) {
        this.branchModelList = branchModelList;
    }

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    public void setCommentModelList(List<CommentModel> commentModelList) {
        this.commentModelList = commentModelList;
    }

    //1 quán ăn có nhiều bình luận
    List<CommentModel> commentModelList;

    public List<String> getHinhanhquanans() {
        return hinhanhquanans;
    }

    public void setHinhanhquanans(List<String> hinhanhquanans) {
        this.hinhanhquanans = hinhanhquanans;
    }

    long luotthich;
    DatabaseReference dbRootNode;

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }


    public RestaurantModel() {
        dbRootNode = FirebaseDatabase.getInstance().getReference();
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getId_quanan() {
        return id_quanan;
    }

    public void setId_quanan(String id_quanan) {
        this.id_quanan = id_quanan;
    }

    public void getListRestaurant(final PlacesInterfaces placesInterfaces, final Location locationCurrent) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotRes = dataSnapshot.child("quanans");
                //getdata restaurant
                for (DataSnapshot value : dataSnapshotRes.getChildren()) {
                    RestaurantModel restaurantModel = value.getValue(RestaurantModel.class);
                    restaurantModel.setId_quanan(value.getKey());
                    //get data res follow userID
                    DataSnapshot dataSnapshotImagesRes = dataSnapshot.child("hinhanhquanans").child(value.getKey());

                    List<String> imagesList = new ArrayList<>();
                    //list đưa ra ngoài bởi vì 1 quán ăn có nhiều tấm hình để khi duyệt được 1 tấm m sẽ lưu vào trong list này
                    for (DataSnapshot valueImagesRes : dataSnapshotImagesRes.getChildren()) {
                        imagesList.add(valueImagesRes.getValue(String.class));
                    }
                    restaurantModel.setHinhanhquanans(imagesList);
//                    //get list comment
                    DataSnapshot snapshotComment = dataSnapshot.child("binhluans").child(restaurantModel.getId_quanan());
                    //key động
                    //vì 1 quán ăn có nhiều bình luận
                    List<CommentModel> commentModels = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshotComment.getChildren()) {
                        CommentModel commentModel = valueBinhLuan.getValue(CommentModel.class);
                        commentModel.setId_comments(valueBinhLuan.getKey());
                        //lấy được dl thành viên của model đó
                        MemberModel memberModel = dataSnapshot.child("thanhviens").child(commentModel.getMauser())
                                .getValue(MemberModel.class);
                        commentModel.setMemberModel(memberModel);
                        //1 bình luận có nhiều tấm hình
                        List<String> imagesCommentList = new ArrayList<>();
                        DataSnapshot dataNodeImagesComment = dataSnapshot.child("hinhanhbinhluans").child(commentModel.getId_comments());
                        for (DataSnapshot valueImagesComment : dataNodeImagesComment.getChildren()) {
                            imagesCommentList.add(valueImagesComment.getValue(String.class));
                        }
                        commentModel.setImagesComentList(imagesCommentList);
                        commentModels.add(commentModel);
                    }
                    restaurantModel.setCommentModelList(commentModels);
                    //get list branch res
                    DataSnapshot dataSnapshotBranch = dataSnapshot.child("chinhanhquanans").child(restaurantModel.getId_quanan());
                    List<BranchModel> listBranchModels = new ArrayList<>();
                    for (DataSnapshot valueBranch : dataSnapshotBranch.getChildren()) {
                        BranchModel branchModel = valueBranch.getValue(BranchModel.class);
                        Location locationBranch = new Location("");
                        locationBranch.setLongitude(branchModel.getLongitude());
                        locationBranch.setLatitude(branchModel.getLatitude());
                        double range = locationCurrent.distanceTo(locationBranch)/1000;
                        branchModel.setRange(range);
                        listBranchModels.add(branchModel);
                    }
                    restaurantModel.setBranchModelList(listBranchModels);
                    placesInterfaces.getListRestaurantModel(restaurantModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRootNode.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //lưu trữ dữ liệu trong Parcel
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giomocua);
        parcel.writeString(giodongcua);
        parcel.writeString(tenquanan);
        parcel.writeString(videogioithieu);
        parcel.writeString(id_quanan);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanans);
        parcel.writeLong(luotthich);
        parcel.writeTypedList(branchModelList);
        parcel.writeTypedList(commentModelList);
    }
}
