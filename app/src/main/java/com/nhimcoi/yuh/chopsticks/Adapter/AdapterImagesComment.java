package com.nhimcoi.yuh.chopsticks.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhimcoi.yuh.chopsticks.Model.CommentModel;
import com.nhimcoi.yuh.chopsticks.R;
import com.nhimcoi.yuh.chopsticks.View.Home.DetailCommentActivity;

import java.util.List;

/**
 * Created by Nhím Còi on 10/27/2017.
 */

public class AdapterImagesComment extends RecyclerView.Adapter<AdapterImagesComment.ViewHolder> {

    Context context;
    int layout;
    List<Bitmap> listImages;
    CommentModel commentModel;
    boolean isDetails;

    public AdapterImagesComment(Context context, int layout, List<Bitmap> listImages, CommentModel commentModel, boolean isDetails) {
        this.context = context;
        this.layout = layout;
        this.listImages = listImages;
        this.commentModel = commentModel;
        this.isDetails = isDetails;
    }

    @Override
    public AdapterImagesComment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterImagesComment.ViewHolder holder, final int position) {
        holder.imgComment.setImageBitmap(listImages.get(position));
        if (!isDetails) {
            if (position == 3) {
                int couunt = listImages.size() - 4;
                holder.frameLayout.setVisibility(View.VISIBLE);
                holder.txtNumber.setText(" + " + couunt);
                if (couunt > 0) {
                    holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, DetailCommentActivity.class);
                            intent.putExtra("commentmodel", commentModel);
                            //context vì đây là adapter
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = listImages.size();
        if (!isDetails && size >= 4) {
            return 4;
        } else {
            return size;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgComment;
        TextView txtNumber;
        FrameLayout frameLayout;
        Toolbar toolbar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgComment = (ImageView) itemView.findViewById(R.id.imagesComment);
            txtNumber = (TextView) itemView.findViewById(R.id.txtNumberComment);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.numberComment);
            toolbar = (Toolbar)itemView.findViewById(R.id.txtTitleToolbar);
        }
    }
}
