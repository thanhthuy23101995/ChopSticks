package com.nhimcoi.yuh.chopsticks.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhimcoi.yuh.chopsticks.Model.BranchModel;
import com.nhimcoi.yuh.chopsticks.Model.CommentModel;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;
import com.nhimcoi.yuh.chopsticks.R;
import com.nhimcoi.yuh.chopsticks.View.Home.DetailResActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nhím Còi on 9/17/2017.
 */

public class AdapterRecyclePlaces extends RecyclerView.Adapter<AdapterRecyclePlaces.ViewHolder> {

    List<RestaurantModel> restaurantModelList;
    int resource;
    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameRes, txtTitleComment1,
                txtTitleComment2, txtComment1,
                txtComment2, txtMark1, txtMark2,
                txtSumComment, txtSimImagesComment,
                txtSumRates,txtAddress,txtRanges;
        Button btnOrder;
        ImageView imgRes;
        CircleImageView imgComment1, imgComment2;
        LinearLayout liner_container1, liner_container2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNameRes = (TextView) itemView.findViewById(R.id.txtNameRes);
            btnOrder = (Button) itemView.findViewById(R.id.btnOderPlaces);
            imgRes = (ImageView) itemView.findViewById(R.id.imgResPlaces);
            txtTitleComment1 = (TextView) itemView.findViewById(R.id.txtTitleComment1);
            txtTitleComment2 = (TextView) itemView.findViewById(R.id.txtTitleComment2);
            txtComment1 = (TextView) itemView.findViewById(R.id.txtComment1);
            txtComment2 = (TextView) itemView.findViewById(R.id.txtComment2);
            imgComment1 = (CircleImageView) itemView.findViewById(R.id.imgComment1);
            imgComment2 = (CircleImageView) itemView.findViewById(R.id.imgComment2);
            liner_container1 = (LinearLayout) itemView.findViewById(R.id.liner_container1);
            liner_container2 = (LinearLayout) itemView.findViewById(R.id.liner_container2);
            txtMark1 = (TextView) itemView.findViewById(R.id.txtMark1);
            txtMark2 = (TextView) itemView.findViewById(R.id.txtMark2);
            txtSumComment = (TextView) itemView.findViewById(R.id.txtSumComment);
            txtSimImagesComment = (TextView) itemView.findViewById(R.id.txtSumImagesComment);
            txtSumRates = (TextView)itemView.findViewById(R.id.txtRates);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtRanges = (TextView)itemView.findViewById(R.id.txtRange);
            cardView = (CardView)itemView.findViewById(R.id.cardViewPlaces);
        }
    }

    //hiển thị list danh sách quán ăn nên truyền vào là giá trị lisr quán ăn
    public AdapterRecyclePlaces(Context context,List<RestaurantModel> restaurantModelList, int resource) {
        this.restaurantModelList = restaurantModelList;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RestaurantModel restaurantModel = restaurantModelList.get(position);
        holder.txtNameRes.setText(restaurantModel.getTenquanan());
        if (restaurantModel.isGiaohang()) {
            holder.btnOrder.setVisibility(View.VISIBLE);
        }
        if (restaurantModel.getHinhanhquanans().size() > 0) {
            StorageReference storageImages = FirebaseStorage.getInstance().getReference().child("hinhanh")
                    .child(restaurantModel.getHinhanhquanans().get(0));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageImages.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imgRes.setImageBitmap(bitmap);
                }
            });
        }
        //get list comment res
        if (restaurantModel.getCommentModelList().size() > 0) {
            CommentModel commentModel = restaurantModel.getCommentModelList().get(0);
            holder.txtTitleComment1.setText(commentModel.getTieude());
            holder.txtComment1.setText(commentModel.getNoidung());
            holder.txtMark1.setText(commentModel.getChamdiem() + "");
            StorageReference storageImagesUser = FirebaseStorage.getInstance().getReference().child("thanhvien")
                    .child(commentModel.getMemberModel().getHinhanh());
            // cái commentModel getImages bị null.
            final long ONE_MEGABYTE = 1024 * 1024;
            storageImagesUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imgComment1.setImageBitmap(bitmap);
                }
            });
            //duyệt all các comment sẽ lấy được các điểm của từng bình luận
            if (restaurantModel.getCommentModelList().size() > 2) {
                CommentModel commentModel2 = restaurantModel.getCommentModelList().get(1);
                holder.txtTitleComment2.setText(commentModel2.getTieude());
                holder.txtComment2.setText(commentModel2.getNoidung());
                holder.txtMark2.setText(commentModel2.getChamdiem() + "");
                Log.d("kt", commentModel.getChamdiem() + "");
                StorageReference storageImagesUser1 = FirebaseStorage.getInstance().getReference().child("thanhvien")
                        .child(commentModel2.getMemberModel().getHinhanh());
                final long ONE_MEGABYTE1 = 1024 * 1024;
                storageImagesUser1.getBytes(ONE_MEGABYTE1).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imgComment2.setImageBitmap(bitmap);
                    }
                });
            }
            holder.txtSumComment.setText(restaurantModel.getCommentModelList().size() + "");
            int sumComment = 0;
            double sumRates = 0;

            //load được từng comment sẽ load được từng điểm quả comment
            //lấy điểm của từng bình luận ra rồi cộng dồn và đếm tổng số hình bình luận
            for (CommentModel commentModels : restaurantModel.getCommentModelList()) {
                sumComment += commentModels.getImagesComentList().size();
                sumRates += commentModels.getChamdiem();
            }
            if (sumComment > 0) {
                holder.txtSimImagesComment.setText(sumComment + "");
            } else {
                holder.txtSimImagesComment.setText("0");
            }
            double sumMedium= sumRates/restaurantModel.getCommentModelList().size();
            holder.txtSumRates.setText(String.format("%.1f",sumMedium));
        } else {
            holder.liner_container1.setVisibility(View.GONE);
            holder.liner_container2.setVisibility(View.GONE);
            holder.txtSumComment.setText("0");
            holder.txtSimImagesComment.setText("0");
        }
        //getListBranch and view address km
        if(restaurantModel.getBranchModelList().size()>0)
        {
            BranchModel branchModeltmp = restaurantModel.getBranchModelList().get(0);
            for(BranchModel valuebranchModel : restaurantModel.getBranchModelList())
            {
                if(branchModeltmp.getRange()> valuebranchModel.getRange())
                {
                    branchModeltmp = valuebranchModel;
                }
            }
            holder.txtAddress.setText(branchModeltmp.getDiachi());
            holder.txtRanges.setText(String.format("%.1f",branchModeltmp.getRange())+" km ");
        }
        //set onclick
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDetailRes = new Intent(context, DetailResActivity.class);
                iDetailRes.putExtra("res",restaurantModel);
                context.startActivity(iDetailRes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantModelList.size();
    }
}


