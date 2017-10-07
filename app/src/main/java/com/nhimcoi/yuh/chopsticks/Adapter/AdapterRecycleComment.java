package com.nhimcoi.yuh.chopsticks.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhimcoi.yuh.chopsticks.Model.CommentModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nhím Còi on 10/3/2017.
 */

public class AdapterRecycleComment extends RecyclerView.Adapter<AdapterRecycleComment.ViewHorlder> {

    Context context;
    int resource;
    List<CommentModel> commentModelList;

    public AdapterRecycleComment(Context context, int resource, List<CommentModel> commentModelList) {
        this.context = context;
        this.resource = resource;
        this.commentModelList = commentModelList;
    }

    public class ViewHorlder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txtTitleComment,txtComment,txtMark;
        public ViewHorlder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView)itemView.findViewById(R.id.circleComment);
            txtTitleComment = (TextView)itemView.findViewById(R.id.txtTitleComment);
            txtComment = (TextView)itemView.findViewById(R.id.txtComment);
            txtMark = (TextView)itemView.findViewById(R.id.txtMark);
        }
    }

    @Override
    public AdapterRecycleComment.ViewHorlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdapterRecycleComment.ViewHorlder viewHorlder = new AdapterRecycleComment.ViewHorlder(view);
        return viewHorlder;
    }

    @Override
    public void onBindViewHolder( final AdapterRecycleComment.ViewHorlder holder, int position) {
        CommentModel commentModel = commentModelList.get(position);
        holder.txtTitleComment.setText(commentModel.getTieude());
        holder.txtComment.setText(commentModel.getNoidung());
        holder.txtMark.setText(commentModel.getChamdiem()+"");
        StorageReference storageImagesUser = FirebaseStorage.getInstance().getReference().child("thanhvien")
                .child(commentModel.getMemberModel().getHinhanh());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageImagesUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        int SumComment = commentModelList.size();
        if (SumComment > 0 && SumComment > 5) {
            return 5;
        } else {
            return SumComment;
        }
    }
}
