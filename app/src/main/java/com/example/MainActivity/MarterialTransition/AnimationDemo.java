package com.example.MainActivity.MarterialTransition;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.MainActivity.BaseActivity;
import com.willprojeck.okhttp.okhttp_text.R;

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
public class AnimationDemo extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftanim);
        final ImageView imageView = (ImageView)findViewById(R.id.img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Animation rotateAnimation = AnimationUtils.loadAnimation(AnimationDemo.this, R.anim.breathinglamp);
                imageView.startAnimation(rotateAnimation);
            }
        }).start();

    }
}
