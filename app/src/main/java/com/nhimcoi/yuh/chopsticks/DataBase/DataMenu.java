package com.nhimcoi.yuh.chopsticks.DataBase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhimcoi.yuh.chopsticks.Adapter.AdapterMenu;
import com.nhimcoi.yuh.chopsticks.Interface.MenuInterface;
import com.nhimcoi.yuh.chopsticks.Model.MenuModel;

import java.util.List;

/**
 * Created by Nhím Còi on 10/7/2017.
 */

public class DataMenu {
    MenuModel menuModel;
    public DataMenu() {
       menuModel = new MenuModel();
    }
    public void getListMenu(final Context context, String id_res, final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        MenuInterface menuInterface = new MenuInterface() {
            @Override
            public void getListMenu_ID(List<MenuModel> menuModelList) {
                AdapterMenu adapterMenu = new AdapterMenu(context,menuModelList);
                recyclerView.setAdapter(adapterMenu);
                adapterMenu.notifyDataSetChanged();
            }
        };
        menuModel.getListMenu(id_res,menuInterface);
    }
}
