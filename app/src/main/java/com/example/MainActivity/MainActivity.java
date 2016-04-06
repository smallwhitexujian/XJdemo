package com.example.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.MainActivity.Material.MaterialMain;
import com.example.MainActivity.Material.RecyclerViewExample;
import com.willprojeck.okhttp.okhttp_text.R;


public class MainActivity extends Activity implements View.OnClickListener{
    private LinearLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        line1 = (LinearLayout) findViewById(R.id.line1);
        addBtn("Marquee跑马灯", 0);
        addBtn("Gps定位", 1);
        addBtn("Fresco图片加载", 2);
        addBtn("okHttp", 3);
        addBtn("简单的播放器", 4);
        addBtn("Material ", 5);
        addBtn("RecyclerViewExample ", 6);

    }

    private void addBtn(String text, int tag) {
        Button btn = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn.setText(text);
        btn.setTag(tag);
        btn.setOnClickListener(this);
        line1.addView(btn, tag, params);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Intent i ;
        switch (tag){
            case 0:
                i = new Intent(MainActivity.this, MarqueeActivity.class);
                startActivity(i);
                break;
            case 1:
                i = new Intent(MainActivity.this, GpsActivity.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(MainActivity.this, Fresco_Demo.class);
                startActivity(i);
                break;
            case 3:
                i = new Intent(MainActivity.this, OKHttpActivity.class);
                startActivity(i);
                break;
            case 4:
                i = new Intent(MainActivity.this, SoundSeekbarActivity.class);
                startActivity(i);
                break;
            case 5:
                i = new Intent(MainActivity.this, MaterialMain.class);
                startActivity(i);
                break;
            case 6:
                i = new Intent(MainActivity.this, RecyclerViewExample.class);
                startActivity(i);
                break;
        }

    }
}