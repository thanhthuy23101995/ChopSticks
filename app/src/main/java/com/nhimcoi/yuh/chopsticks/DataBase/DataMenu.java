package com.nhimcoi.yuh.chopsticks.DataBase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Nhím Còi on 10/7/2017.
 */

public class DataMenu {
    public void getListMenu(String maquanan)
    {
        DatabaseReference nodeMenuRes = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeMenuRes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot value : dataSnapshot.getChildren())
                {
                    DatabaseReference nodeMenu = FirebaseDatabase.getInstance().getReference().child("thucdons").child(value.getKey());
                    nodeMenu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("check"," "+dataSnapshot);
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
