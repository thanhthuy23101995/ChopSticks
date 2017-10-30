package com.nhimcoi.yuh.chopsticks.View.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhimcoi.yuh.chopsticks.Adapter.AdapterRecycleComment;
import com.nhimcoi.yuh.chopsticks.DatMonUtils;
import com.nhimcoi.yuh.chopsticks.DataBase.DataMenu;
import com.nhimcoi.yuh.chopsticks.Model.RestaurantModel;
import com.nhimcoi.yuh.chopsticks.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DetailResActivity extends AppCompatActivity implements OnMapReadyCallback{

    TextView txtName, txtAddress, txtStatus,
            txtTime, txtSumPhoTo, txtSumComent,
            txtSumCheckIn, txtSumSave, txtTitleToolBar,txtPrices;
    ImageView imgImages;
    RestaurantModel restaurantModel;
    Toolbar toolbar;
    RecyclerView recyclerViewComment;
    NestedScrollView nestedScrollView;
    GoogleMap googleMap;
    MapFragment mapFragment;
    DataMenu dataMenu ;
    RecyclerView recyclerViewMenu;
    LinearLayout linerFeatures;

    /**
     * Send notification
     */
    Button mBtDatMon;
    private final String mUser = "Tran Thi Hong Tham";
    private final String mStore = "GOGI House";

    private final View.OnClickListener mDatMonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatabaseReference datMonReference = FirebaseDatabase.getInstance().getReference("zDatMonAn").push();
            datMonReference.child("user").setValue(mUser);
            datMonReference.child("store").setValue(mStore);
            datMonReference.child("danhSachMonAn").setValue(DatMonUtils.getListMonAnDuocDat());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_res);
        restaurantModel = getIntent().getParcelableExtra("res");
        txtName = (TextView) findViewById(R.id.txtNameResDetail);
        txtAddress = (TextView) findViewById(R.id.txtAddressRes);
        txtPrices = (TextView)findViewById(R.id.txtPrice);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtTime = (TextView) findViewById(R.id.txtOpen);
        txtSumPhoTo = (TextView) findViewById(R.id.txtSumPhoto);
        txtSumComent = (TextView) findViewById(R.id.txtSumReviews);
        txtSumCheckIn = (TextView) findViewById(R.id.txtSumCheckIn);
        txtSumSave = (TextView) findViewById(R.id.txtSumSave);
        imgImages = (ImageView) findViewById(R.id.imgImages);
        txtTitleToolBar = (TextView) findViewById(R.id.txtTitleToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbarLayout);
        recyclerViewComment = (RecyclerView)findViewById(R.id.recycComment);
        nestedScrollView = (NestedScrollView)findViewById(R.id.scrollViewDetail);
        recyclerViewMenu = (RecyclerView)findViewById(R.id.recyc_menu);
        linerFeatures = (LinearLayout)findViewById(R.id.linerFeatures);
        mBtDatMon = findViewById(R.id.btnDatMon);
        mBtDatMon.setOnClickListener(mDatMonListener);
        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dataMenu = new DataMenu();
        mapFragment.getMapAsync(this);
        DisplayResDetail();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DisplayResDetail()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String HoursCurrent = dateFormat.format(calendar.getTime());
        Log.d("hi",HoursCurrent);
        String HoursOpen = restaurantModel.getGiomocua();
        String HoursClose = restaurantModel.getGiodongcua();
        try {
            Date dateCurrent = dateFormat.parse(HoursCurrent);
            Date dateOpen = dateFormat.parse(HoursOpen);
            Date dateClose = dateFormat.parse(HoursClose);
            if(dateCurrent.after(dateOpen)&&dateCurrent.before(dateClose))
            {
                txtStatus.setText(getString(R.string.Open));
            }
            else
            {
                txtStatus.setText(getString(R.string.Close));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtName.setText(restaurantModel.getTenquanan());
        txtTitleToolBar.setText(restaurantModel.getTenquanan());
        txtAddress.setText(restaurantModel.getBranchModelList().get(0).getDiachi());
        txtTime.setText(restaurantModel.getGiomocua() + " - " + restaurantModel.getGiodongcua());
        txtSumPhoTo.setText(restaurantModel.getHinhanhquanans().size() + "");
        txtSumComent.setText(restaurantModel.getCommentModelList().size() + "");
        txtTime.setText(HoursOpen + " - " + HoursClose);
        if(restaurantModel.getGiatoida() !=0 && restaurantModel.getGiatoithieu() !=0)
        {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(restaurantModel.getGiatoithieu())+ " đ ";
            String giatoida = numberFormat.format(restaurantModel.getGiatoida())+ " đ ";
            txtPrices.setText(giatoithieu + " - " +  giatoida);
        }
        else
        {
            txtPrices.setVisibility(View.INVISIBLE);
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh")
                .child(restaurantModel.getHinhanhquanans().get(0));
        final long ONE_MEGABYTE1 = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE1).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgImages.setImageBitmap(bitmap);
            }
        });
        //load list comment
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(layoutManager);
        AdapterRecycleComment adapterRecycleComment = new AdapterRecycleComment(this,R.layout.custom_layout_comment,
                restaurantModel.getCommentModelList());
        recyclerViewComment.setAdapter(adapterRecycleComment);
        adapterRecycleComment.notifyDataSetChanged();
        nestedScrollView.smoothScrollTo(0,0);
        dataMenu.getListMenu(this,restaurantModel.getId_quanan(),recyclerViewMenu);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        double latiude = restaurantModel.getBranchModelList().get(0).getLatitude();
        Log.e("check",latiude+"");
        double longitude = restaurantModel.getBranchModelList().get(0).getLongitude();
        LatLng latLng = new LatLng(latiude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(restaurantModel.getTenquanan());
        googleMap.addMarker(markerOptions);
        googleMap.setMaxZoomPreference(20.0F);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
    }
    public void DowndLoad(LinearLayout linearLayout)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh")
                .child(restaurantModel.getHinhanhquanans().get(0));
        final long ONE_MEGABYTE1 = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE1).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgImages.setImageBitmap(bitmap);
            }
        });
    }
}
