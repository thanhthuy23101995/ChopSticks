package com.nhimcoi.yuh.chopsticks.View.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhimcoi.yuh.chopsticks.Adapter.AdapterImagesComment;
import com.nhimcoi.yuh.chopsticks.Model.CommentModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailCommentActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView txtTitleComment, txtComment, txtMark;
    RecyclerView recyclerViewImagesComment;
    List<Bitmap> bitmapList;
    CommentModel commentModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_comment);
        circleImageView = (CircleImageView) findViewById(R.id.circleComment);
        txtTitleComment = (TextView) findViewById(R.id.txtTitleComment);
        txtComment = (TextView) findViewById(R.id.txtComment);
        txtMark = (TextView) findViewById(R.id.txtMark);
        recyclerViewImagesComment = (RecyclerView) findViewById(R.id.recycleImagesComment);
        bitmapList = new ArrayList<>();
        commentModel = getIntent().getParcelableExtra("commentmodel");
        txtTitleComment.setText(commentModel.getTieude());
        txtComment.setText(commentModel.getNoidung());
        txtMark.setText(commentModel.getChamdiem() + "");
        StorageReference storageImagesUser = FirebaseStorage.getInstance().getReference().child("thanhvien")
                .child(commentModel.getMemberModel().getHinhanh());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageImagesUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
        for (String imagesList : commentModel.getImagesComentList()) {
            StorageReference storageImages = FirebaseStorage.getInstance().getReference().child("hinhanh")
                    .child(imagesList);
            final long ONE_MEGABYTE_IMAGES_COMMENT = 1024 * 1024;
            storageImages.getBytes(ONE_MEGABYTE_IMAGES_COMMENT).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmapImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmapImg);
                    Log.e("size", bitmapList.size() + "");
                    if (bitmapList.size() == commentModel.getImagesComentList().size()) {
                        Log.e("size", bitmapList.size() + "");
                        AdapterImagesComment adapterImagesComment = new AdapterImagesComment(DetailCommentActivity.this, R.layout.custom_layout_imagescomment, bitmapList, commentModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DetailCommentActivity.this, 2);
                        recyclerViewImagesComment.setLayoutManager(layoutManager);
                        recyclerViewImagesComment.setAdapter(adapterImagesComment);
                        adapterImagesComment.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
