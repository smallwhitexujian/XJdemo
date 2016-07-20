package com.example.MainActivity.MarterialTransition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.utils.DebugLogs;
import com.xj.utils.utils.ToastUtils;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/13
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/13          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class AnimationDemo extends Activity implements View.OnClickListener {
    private Button btn, btn1, btn2;
    private LinearLayout line1;
    private TextView textView;
    private ImageView imageView,imageView2;
    private Animation translateAnimation_in,translateAnimation_out,translate_in, scaleAnimation,freefallUp;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftanim);
        imageView = (ImageView) findViewById(R.id.img);
        imageView2 = (ImageView) findViewById(R.id.img2);
        line1 = (LinearLayout) findViewById(R.id.line1);
        textView = (TextView) findViewById(R.id.numText);
        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        line1.setVisibility(View.VISIBLE);
        translateAnimation_in = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.fade_in_anim);
        translate_in = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.fade2_in_anim);
        freefallUp = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.amin_shock);
        imageView2.startAnimation(freefallUp);
        translateAnimation_out = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.fade_out_anim);
        scaleAnimation = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.thepinanim);

        translateAnimation_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DebugLogs.d("-------+动画结束--->");
                textView.setVisibility(View.VISIBLE);
                textView.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                line1.startAnimation(translateAnimation_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        line1.startAnimation(translateAnimation_in);
        imageView.startAnimation(translate_in);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                line1.setVisibility(View.VISIBLE);
                line1.startAnimation(translateAnimation_in);
                imageView.startAnimation(translate_in);
                translateAnimation_in.reset();
                translate_in.reset();
                break;
            case R.id.btn1:
                ToastUtils.showToast(AnimationDemo.this,"添加数据");
                break;
            case R.id.btn2:
                i++;
                String str = "x"+i;
                translateAnimation_out.start();
                textView.setText(str);
                textView.startAnimation(scaleAnimation);
                break;
        }
    }
}
