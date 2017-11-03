package com.nhimcoi.yuh.chopsticks.View.Home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nhimcoi.yuh.chopsticks.Adapter.AdapterViewpagerHome;
import com.nhimcoi.yuh.chopsticks.R;

public class HomePageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewPagerHome;
    RadioButton mrd_places, mrd_food;
    RadioGroup mradioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        viewPagerHome = (ViewPager) findViewById(R.id.viewPager_Home);
        mrd_places = (RadioButton) findViewById(R.id.rd_places);
        mrd_food = (RadioButton) findViewById(R.id.rd_food);
        mradioGroup = (RadioGroup) findViewById(R.id.group);
        AdapterViewpagerHome adapterViewpagerHome = new AdapterViewpagerHome(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapterViewpagerHome);
        viewPagerHome.addOnPageChangeListener(this);
        mradioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mrd_places.setChecked(true);
                Log.e("hi", "plas");
                break;
            case 1:
                mrd_food
                        .setChecked(true);
                Log.e("hi", "food");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rd_places:
                viewPagerHome.setCurrentItem(0);
                break;
            case R.id.rd_food:
                viewPagerHome.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
