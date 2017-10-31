package com.nhimcoi.yuh.chopsticks.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nhimcoi.yuh.chopsticks.DataBase.DataPlaces;
import com.nhimcoi.yuh.chopsticks.R;


public class FragmentPlaces extends Fragment {
    RecyclerView recyclerView;
    DataPlaces dataPlaces;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_places, container, false);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_palces);
        progressBar = (ProgressBar) view.findViewById(R.id.progres_places);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("coordinator", Context.MODE_PRIVATE);
        Location locationCurrent = new Location("");
        locationCurrent.setLatitude(Double.parseDouble(sharedPreferences.getString("Latitude","0")));
        locationCurrent.setLongitude(Double.parseDouble(sharedPreferences.getString("Longitude","0")));
        Log.e("checklocation",locationCurrent.getLatitude()+"");
        dataPlaces = new DataPlaces(getContext());
        dataPlaces.getListRestaurantModel(getContext(),recyclerView, progressBar,locationCurrent);
    }
}
