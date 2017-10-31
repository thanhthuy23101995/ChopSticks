package com.nhimcoi.yuh.chopsticks.Model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhím Còi on 10/31/2017.
 */

public class ParserPoLyLine {
    public ParserPoLyLine() {
    }

    public List<LatLng> getListMaps(String dataJson) throws JSONException {
        List<LatLng> latLngs = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray routes = jsonObject.getJSONArray("routes");
            for (int i=0; i<routes.length(); i++){
                JSONObject jsonObjectArray = routes.getJSONObject(i);
                JSONObject overviewPolyline = jsonObjectArray.getJSONObject("overview_polyline");

                String point = overviewPolyline.getString("points");
                Log.d("kiemtra",point + " ");
                latLngs = PolyUtil.decode(point);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return latLngs;
    }
}
