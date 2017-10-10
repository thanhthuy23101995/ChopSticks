package com.nhimcoi.yuh.chopsticks.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhimcoi.yuh.chopsticks.Interface.MenuInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhím Còi on 10/7/2017.
 */

public class MenuModel {
    private String mathucdon;
    private String tenthucdon;

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

    List<FoodModel> foodModelList;

    public void getListMenu(String id_res, final MenuInterface menuInterface) {
        DatabaseReference nodeMenuRes = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(id_res);
        nodeMenuRes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<MenuModel> menuModelList = new ArrayList<MenuModel>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    final MenuModel menuModel = new MenuModel();
                    DatabaseReference nodeMenu = FirebaseDatabase.getInstance().getReference().child("thucdons").child(value.getKey());
                    nodeMenu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotMenu) {
                            menuModel.setMathucdon(mathucdon);
                            menuModel.setTenthucdon(dataSnapshotMenu.getValue(String.class));
                            List<FoodModel> foodModelList = new ArrayList<FoodModel>();
                            for (DataSnapshot valueFood : dataSnapshot.child(dataSnapshotMenu.getKey()).getChildren()) {
                                FoodModel foodModel = valueFood.getValue(FoodModel.class);
                                foodModel.setMamon(valueFood.getKey());
                                foodModelList.add(foodModel);
                            }
                            menuModel.setFoodModelList(foodModelList);
                            menuModelList.add(menuModel);
                            menuInterface.getListMenu_ID(menuModelList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
