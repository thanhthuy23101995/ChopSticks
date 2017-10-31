package com.nhimcoi.yuh.chopsticks.View.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nhimcoi.yuh.chopsticks.DataBase.DataGoogleMaps;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.util.concurrent.ExecutionException;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude = 0;
    double longitude = 0;
    SharedPreferences sharedPreferences;
    Location locationCurrent;
    DataGoogleMaps dataGoogleMaps;
    //https://maps.googleapis.com/maps/api/directions/json?origin=Brooklyn&destination=Queens&mode=transit&key=YOUR_API_KEY
    String link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        dataGoogleMaps = new DataGoogleMaps();
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        sharedPreferences = getSharedPreferences("coordinator", Context.MODE_PRIVATE);
         locationCurrent = new Location("");
        locationCurrent.setLatitude(Double.parseDouble(sharedPreferences.getString("Latitude","0")));
        locationCurrent.setLongitude(Double.parseDouble(sharedPreferences.getString("Longitude","0")));
        link =  "https://maps.googleapis.com/maps/api/directions/json?origin=" + locationCurrent.getLatitude() + "," + locationCurrent.getLongitude() + "&destination=" +latitude+"," + longitude + "&language=vi&key=AIzaSyC0VvNOWC6rtGdeHnkBrPBYwURK8uazBfI";
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.clear();
        RestaurantModel restaurantModel = new RestaurantModel();

        LatLng latLng = new LatLng(locationCurrent.getLatitude(), locationCurrent.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(restaurantModel.getTenquanan());
        googleMap.addMarker(markerOptions);

        LatLng locationRes = new LatLng(latitude,longitude);
        MarkerOptions options = new MarkerOptions();
        options.position(locationRes);
        options.title(restaurantModel.getTenquanan());
        googleMap.addMarker(options);

        googleMap.setMaxZoomPreference(20.0F);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
        try {
            dataGoogleMaps.GetGoogleMaps(googleMap,link);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
