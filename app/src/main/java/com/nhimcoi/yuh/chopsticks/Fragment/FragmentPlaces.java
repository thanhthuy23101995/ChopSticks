package com.nhimcoi.yuh.chopsticks.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nhimcoi.yuh.chopsticks.Adapter.AdapterRecyclePlaces;
import com.nhimcoi.yuh.chopsticks.DataBase.DataPlaces;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.List;


public class FragmentPlaces extends Fragment {
    RecyclerView recyclerView;
    DataPlaces dataPlaces;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    AdapterRecyclePlaces adapterRecyclePlaces;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_places, container, false);
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences("coordinator", Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_palces);
        progressBar = (ProgressBar) view.findViewById(R.id.progres_places);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Location locationCurrent = new Location("");
        locationCurrent.setLatitude(Double.parseDouble(sharedPreferences.getString("Latitude", "0")));
        locationCurrent.setLongitude(Double.parseDouble(sharedPreferences.getString("Longitude", "0")));
        Log.e("checklocation", locationCurrent.getLatitude() + "");
        dataPlaces = new DataPlaces(getContext());
        dataPlaces.getListRestaurant(locationCurrent, new DataPlaces.ThhuyDoHoiCallBack() {
            @Override
            public void onGetDataCompleted(List<RestaurantModel> data) {
                if(!data.isEmpty())
                {
                    adapterRecyclePlaces = new AdapterRecyclePlaces(getContext(),data, R.layout.layout_custom_palces);
                    recyclerView.setAdapter(adapterRecyclePlaces);
                    adapterRecyclePlaces.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        //=>> done - ok man? để e chjay thu ạ
        //List<RestaurantModel> restaurantModelList = dataPlaces.getListRestaurant(locationCurrent);


      //  dataPlaces.getListRestaurantModel(getContext(),recyclerView, progressBar, locationCurrent);
    }
}
