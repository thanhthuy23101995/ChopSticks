package com.nhimcoi.yuh.chopsticks.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhimcoi.yuh.chopsticks.Model.MenuModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.List;

/**
 * Created by Nhím Còi on 10/9/2017.
 */

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.HolderMenu> {
    Context context;
    List<MenuModel> menuModelList;
    public AdapterMenu(Context context, List<MenuModel> menuModelList)
    {
        this.context = context;
        this.menuModelList = menuModelList;
    }
    public class HolderMenu extends RecyclerView.ViewHolder {
        TextView txtMenu;
        RecyclerView recyclerViewMenu;
        public HolderMenu(View itemView) {
            super(itemView);
            txtMenu = (TextView)itemView.findViewById(R.id.txtTitleMenu);
            recyclerViewMenu = (RecyclerView)itemView.findViewById(R.id.recyc_food);
        }
    }

    @Override
    public AdapterMenu.HolderMenu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_menu,parent,false);
        return new HolderMenu(view);
    }

    @Override
    public void onBindViewHolder(AdapterMenu.HolderMenu holder, int position) {
        MenuModel menuModel = menuModelList.get(position);
        holder.txtMenu.setText(menuModel.getTenthucdon());
        holder.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(context));
        AdapterFood adapterFood = new AdapterFood(context,menuModel.getFoodModelList());
        holder.recyclerViewMenu.setAdapter(adapterFood);
        adapterFood.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return menuModelList.size();
    }
}
