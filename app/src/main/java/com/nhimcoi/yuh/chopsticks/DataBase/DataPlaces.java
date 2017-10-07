package com.nhimcoi.yuh.chopsticks.DataBase;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.nhimcoi.yuh.chopsticks.Adapter.AdapterRecyclePlaces;
import com.nhimcoi.yuh.chopsticks.Interface.PlacesInterfaces;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhím Còi on 9/17/2017.
 */

public class DataPlaces {
    Context context;
    RestaurantModel restaurantModel;
    AdapterRecyclePlaces adapterRecyclePlaces;
    public DataPlaces(Context context) {
        this.context = context;
        restaurantModel = new RestaurantModel();
    }
    public void getListRestaurantModel(Context context,RecyclerView recyclerView, final ProgressBar progressBar, Location locationCurrent)
    {
        final List<RestaurantModel> restaurantModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
         adapterRecyclePlaces = new AdapterRecyclePlaces(context,restaurantModelList, R.layout.layout_custom_palces);
        recyclerView.setAdapter(adapterRecyclePlaces);
        PlacesInterfaces placesInterfaces = new PlacesInterfaces() {
            @Override
            public void getListRestaurantModel(RestaurantModel restaurantModel) {
                restaurantModelList.add(restaurantModel);
                adapterRecyclePlaces.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };
        restaurantModel.getListRestaurant(placesInterfaces,locationCurrent);
    }
}
