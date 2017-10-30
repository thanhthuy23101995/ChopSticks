package com.nhimcoi.yuh.chopsticks.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nhimcoi.yuh.chopsticks.R;
import com.nhimcoi.yuh.chopsticks.View.Home.HomePageActivity;
import com.nhimcoi.yuh.chopsticks.View.Login_Signup.LoginActivity;

public class SlashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView txtVersion;
    TextView txtLoading;
    GoogleApiClient mgoogleApiClient;
    SharedPreferences sharedPreferences;
    //The callback method gets the
    // result of the request.
    public static final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slash_activity);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtLoading = (TextView) findViewById(R.id.txtLoad);
        sharedPreferences = getSharedPreferences("coordinator", MODE_PRIVATE);
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this) //mở kết nối xem kết nối thành công chưa -> thành công thù lấy location
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // kierm tra xem người dùng đã xin quyền hay chưa
        int checkPermissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermissionLocation != PackageManager.PERMISSION_GRANTED) {
            //chưa kết nối thì xin quyền
            //1 mảng kiểu string chứa all các quyền
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            // kết nối được rồ thì mở kết nối
            mgoogleApiClient.connect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mgoogleApiClient.connect();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //đã kết nối gg play services
        Location getLocation = LocationServices.FusedLocationApi.getLastLocation(mgoogleApiClient);
        if (getLocation != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Latitude", String.valueOf(getLocation.getLatitude()));
            editor.putString("Longitude", String.valueOf(getLocation.getLongitude()));
            editor.commit();
        }
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtVersion.setText(" Version " + " " + packageInfo.versionName);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent iLogin = new Intent(SlashScreenActivity.this, LoginActivity.class);
                        startActivity(iLogin);
                        finish();
                    }
                }
            });
            thread.start();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
