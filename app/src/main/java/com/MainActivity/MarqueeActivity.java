package com.MainActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.willprojeck.okhttp.okhttp_text.R;

import java.util.ArrayList;
import java.util.HashMap;

import Marquee.MarqueeUilts;


/**
 * Created by xujian on 16/2/17.
 * 跑马灯Demo
 *      1，可以使用Spanned进行图文混排，
 *      2，可以带图片
 *      3，自动调节跑马灯移动速度，
 *      4，跑马灯队列控制跑完一个才跑下一个，
 */
public class MarqueeActivity extends Activity {
    private LinearLayout marqueeLayout;
    private EditText editText;
    private AnimationSet anim;
    private ImageView imgView;
    private static ArrayList<HashMap<String, Object>> marqueeData = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_marquee);
        marqueeLayout = (LinearLayout) findViewById(R.id.marquee);
        Button btn = (Button) findViewById(R.id.btn);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button button = (Button) findViewById(R.id.button);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button stop = (Button) findViewById(R.id.stop);
        imgView = (ImageView) findViewById(R.id.imgView);
        editText = (EditText) findViewById(R.id.editText);

        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> params = new HashMap<>();
            Spanned s = Html.fromHtml("" + i);
            params.put(MarqueeUilts.CONTEXT, s);
            Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iocon);
            params.put(MarqueeUilts.BITMAP, rawBitmap);
            marqueeData.add(params);
        }

        final MarqueeUilts marqueeUilts = new MarqueeUilts(MarqueeActivity.this,marqueeData,marqueeLayout);
        marqueeUilts.Start();
        //下面等同于上面
//        MarqueeUilts marqueeUilts = new MarqueeUilts();
//        anim = marqueeUilts.StartAnim(new MarqueeUilts.callBack() {
//            @Override
//            public void Start(Animation animation) {
//                marqueeLayout.invalidate();
//                marqueeLayout.removeAllViews();
//                if (marqueeData.size() > 0) {
//                    Spanned context = (Spanned) marqueeData.get(0).get("context");
//                    TextView tv= new TextView(MarqueeActivity.this);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT);
//                    tv.setText(context);
//                    marqueeLayout.addView(tv,0,layoutParams);
//                    if (marqueeData.get(0).get("img").toString().equals("1")) {
//                        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iocon);
//                        imgView.setImageBitmap(rawBitmap);
//                        marqueeLayout.addView(imgView);
//                    }
//                    marqueeData.remove(0);
//                }
//                marqueeLayout.startAnimation(anim);
//                marqueeLayout.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void End(Animation animation) {
//                if (marqueeData.size() > 0) {
//                    anim.start();
//                }
//                marqueeLayout.setVisibility(View.GONE);
//                marqueeLayout.removeAllViews();
//            }
//        });
//        marqueeLayout.startAnimation(anim);


        /*********** 添加事件 ***************/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (anim.hasEnded() && marqueeData.size() == 0) {
//                    anim.start();
//                    marqueeLayout.startAnimation(anim);
//                }
                marqueeUilts.restartAnim();
                HashMap<String, Object> params = new HashMap<>();
                String str1 = "<font color='#F06B00'><b>" + "房间ID:100086" + "</b></font> ";
                String str2 = "<font color='#123123'><b>" + "许健" + "</b></font> ";
                String str3 = "<font color='#765443'><b>" + "对大家说" + "</b></font> ";
                String str4 = "<font color='#512356'><b>" + "跑马灯 哼哼哼哼嗯哼---->跑马灯哼哼哼哼嗯哼---->跑马灯" + "</b></font> ";
                Spanned s = Html.fromHtml(str1 + str2 + str3 + str4);
                params.put("context", s);
                params.put("img", "0");
                marqueeData.add(params);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (anim != null && marqueeLayout != null) {
//                    anim.start();
//                    marqueeLayout.startAnimation(anim);
//                }
                //上下等同
                marqueeUilts.restart();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> params = new HashMap<>();
                Spanned s = Html.fromHtml(editText.getText().toString());
                params.put("context", s);
                params.put("img", "0");
                marqueeData.add(params);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                HashMap<String, Object> params = new HashMap<>();
                Spanned s = Html.fromHtml("增加的" + i);
                params.put("context", s);
                params.put("img", "1");
                marqueeData.add(params);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnim();
            }
        });
    }

    /**
     * 停止启动动画
     */
    private void stopAnim() {
        if (anim != null && marqueeLayout != null) {
            marqueeLayout.clearAnimation();
            anim.cancel();
        }
    }

}
