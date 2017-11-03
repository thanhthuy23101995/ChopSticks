package com.nhimcoi.yuh.chopsticks.DataBase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nhimcoi.yuh.chopsticks.Model.DowLoadPoLyLine;
import com.nhimcoi.yuh.chopsticks.Model.ParserPoLyLine;
import com.nhimcoi.yuh.chopsticks.R;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nhím Còi on 10/31/2017.
 */

public class DataGoogleMaps {
    ParserPoLyLine parserPoLyLine;
    DowLoadPoLyLine dowLoadPoLyLine;
    public DataGoogleMaps() {
    }
    public void GetGoogleMaps(GoogleMap googleMap, String link) throws ExecutionException, InterruptedException {
        parserPoLyLine = new ParserPoLyLine();
        googleMap.clear();
        dowLoadPoLyLine = new DowLoadPoLyLine();
        dowLoadPoLyLine.execute(link);
        try {
            String dataJson = dowLoadPoLyLine.get();
            parserPoLyLine.getListMaps(dataJson);
            List<LatLng> latLngList = parserPoLyLine.getListMaps(dataJson);
            PolylineOptions polylineOptions = new PolylineOptions();
            for(LatLng latLng : latLngList)
            {
                polylineOptions.add(latLng);
            }
            Polyline polyline = googleMap.addPolyline(polylineOptions);
            polyline.setColor(R.color.colorPrimary);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
