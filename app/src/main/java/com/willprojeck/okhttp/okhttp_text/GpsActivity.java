package com.willprojeck.okhttp.okhttp_text;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import GpsTracker.GpsTracker;

/**
 * Created by xujian on 16/2/18.
 * GPS定位展示地点
 */
public class GpsActivity extends Activity implements View.OnClickListener{
    private TextView results;
    private Button btn,btn1,btn2,btn3,btn4;
    private GpsTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        results = (TextView)findViewById(R.id.results);
        btn = (Button)findViewById(R.id.btn);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        gpsTracker = new GpsTracker(GpsActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn://经纬度
                String str = gpsTracker.getLocation()+" - , - "+gpsTracker.getLocation();
                results.setText(str);
                break;
            case R.id.btn1://获取城市
                results.setText(gpsTracker.getCity());
                break;
            case R.id.btn2:
                results.setText(gpsTracker.getAddress());
                break;
            case R.id.btn3:
                results.setText(gpsTracker.getCanCity().getCountryName());
                break;
            case R.id.btn4:
                results.setText(gpsTracker.getAddMessage().toString());
                break;
        }
    }
}
