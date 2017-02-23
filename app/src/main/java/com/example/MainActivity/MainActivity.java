package com.example.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.MainActivity.MarterialTransition.AnimationDemo;
import com.example.MainActivity.Material.MaterialMain;
import com.example.MainActivity.Material.RecyclerViewExample;
import com.example.MainActivity.Recycleview.RecycleviewActivity;
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
        addBtn("RecyclerView list ", 6);
        addBtn("RecyclerViewExample GridView ", 7);
        addBtn("RecyclerView 瀑布流", 8);
        addBtn("呼吸灯效果", 9);
        addBtn("RecycleviewActivity", 10);
        addBtn("标签瀑布流", 11);
        addBtn("计时器", 12);
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
                i.putExtra("GRID", 1);
                startActivity(i);
                break;
            case 7:
                i = new Intent(MainActivity.this, RecyclerViewExample.class);
                i.putExtra("GRID", 2);
                startActivity(i);
                break;
            case 8:
                i = new Intent(MainActivity.this, RecyclerViewExample.class);
                i.putExtra("GRID", 3);
                startActivity(i);
                break;
            case 9:
                i = new Intent(MainActivity.this, AnimationDemo.class);
                startActivity(i);
                break;
            case 10:
                i = new Intent(MainActivity.this, RecycleviewActivity.class);
                startActivity(i);
                break;
            case 11:
                i = new Intent(MainActivity.this, exampleDemo.class);
                startActivity(i);
                break;
            case 12:
                i = new Intent(MainActivity.this, ChronometerActivity.class);
                startActivity(i);
                break;
        }

    }

}
