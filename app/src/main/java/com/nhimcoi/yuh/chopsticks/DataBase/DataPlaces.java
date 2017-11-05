package com.nhimcoi.yuh.chopsticks.DataBase;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhimcoi.yuh.chopsticks.Adapter.AdapterRecyclePlaces;
import com.nhimcoi.yuh.chopsticks.Interface.PlacesInterfaces;
import com.nhimcoi.yuh.chopsticks.Model.BranchModel;
import com.nhimcoi.yuh.chopsticks.Model.CommentModel;
import com.nhimcoi.yuh.chopsticks.Model.MemberModel;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhím Còi on 9/17/2017.
 */

public class DataPlaces {
    Context context;
    RestaurantModel restaurantModel;
    AdapterRecyclePlaces adapterRecyclePlaces;
    DatabaseReference dbRootNode; //nhin thay bon kia no khoi tao chua? ok? // thang nay chua duoc khoi tao thi lam sao chay duoc? vai tro cua no la gi? neu khong can thi xoa di

    public DataPlaces(Context context) {
        this.context = context;
        restaurantModel = new RestaurantModel();
//        vi du se lam the nay
        dbRootNode = FirebaseDatabase.getInstance().getReference();
    }
    public void getListRestaurant(final Location locationCurrent, final PlacesInterfaces placesInterfaces) {
        //ham nay chay async => ko return data o day duoc => phai doi kieu voice va add caall back cho no
        //Lam nhu the nay (vi du)
        final List<RestaurantModel> restaurantModelList = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotRes = dataSnapshot.child("quanans");
                //getdata restaurant
                for (DataSnapshot value : dataSnapshotRes.getChildren()) {
                    RestaurantModel restaurantModel = value.getValue(RestaurantModel.class);
                    restaurantModel.setId_quanan(value.getKey());
                    Log.e("giatien", restaurantModel.getGiatoida() + "");
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
                        double range = locationCurrent.distanceTo(locationBranch) / 1000;
                        branchModel.setRange(range);
                        listBranchModels.add(branchModel);
                    }
                    restaurantModel.setBranchModelList(listBranchModels);
                    restaurantModelList.add(restaurantModel);
                    // placesInterfaces.getListRestaurantModel(restaurantModel);
                    if (placesInterfaces != null) {
                        placesInterfaces.getListRestaurantModel(restaurantModelList);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (placesInterfaces != null) {
                    placesInterfaces.getListRestaurantModel(new ArrayList<RestaurantModel>());
                    // tra ve rong thay vi null - do i crash neu ko handle exception chan
                    Log.e("check", "null");
                }
            }
        };
//        no bao dbRootNode null
//                thi lam the nay
        if (dbRootNode != null) {
            dbRootNode.addListenerForSingleValueEvent(valueEventListener);
            // hieu tai sao co cai doan nay ko?
//           1. Tao DB lien ket voi firebase: dbRootNode = FirebaseDatabase.getInstance().getReference("ten cua cai table tren firebase");
//           2. vi db nay la async nen can add call back:  dbRootNode.addListenerForSingleValueEvent(valueEventListener);
//            3. data tra ve thong qua call back valueEventListener
//            ok?
            // ys em l ban dau khoi tao lay node tren cung roi vao trong ms duyet tung node con
        }


    }


}
