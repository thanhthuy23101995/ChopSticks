package com.nhimcoi.yuh.chopsticks.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhimcoi.yuh.chopsticks.DatMonUtils;
import com.nhimcoi.yuh.chopsticks.Model.FoodModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.List;

/**
 * Created by Nhím Còi on 10/9/2017.
 */

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    Context context;
    List<FoodModel> foodModelList;
    public AdapterFood(Context context, List<FoodModel> foodModelList) {
        this.context =context;
        this.foodModelList=foodModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_food,parent,false);
        return new AdapterFood.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FoodModel foodModel = foodModelList.get(position);
        holder.txtFood.setText(foodModel.getTenmon());
        holder.txtNumber.setTag(0);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int account = Integer.parseInt(holder.txtNumber.getTag()+"");
                account++;
                holder.txtNumber.setText(account+"");
                holder.txtNumber.setTag(account);

                DatMonUtils.addMonAn(foodModel);
            }
        });
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int account = Integer.parseInt(holder.txtNumber.getTag()+"");
                if(account != 0)
                {
                    account--;
                }
                holder.txtNumber.setText(account+"");
                holder.txtNumber.setTag(account);

                DatMonUtils.removeMonAn(foodModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFood,txtNumber;
        ImageView imgRemove,imgAdd;
        public ViewHolder(View itemView) {
            super(itemView);
            txtFood = (TextView)itemView.findViewById(R.id.txtNameFoody);
            txtNumber = (TextView)itemView.findViewById(R.id.txtNumber);
            imgAdd = (ImageView) itemView.findViewById(R.id.imgAdd);
            imgRemove = (ImageView) itemView.findViewById(R.id.imgRemove);
        }
    }
}
