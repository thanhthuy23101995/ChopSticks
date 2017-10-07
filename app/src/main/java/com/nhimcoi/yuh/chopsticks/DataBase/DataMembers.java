package com.nhimcoi.yuh.chopsticks.DataBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhimcoi.yuh.chopsticks.Model.MemberModel;

/**
 * Created by Nhím Còi on 9/23/2017.
 */

public class DataMembers {
    DatabaseReference dataNodeMembers ;
    public DataMembers() {
        dataNodeMembers = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    public void AddMemberModel(MemberModel memberModel,String userID) {
        dataNodeMembers.child(userID).setValue(memberModel);
    }
}
