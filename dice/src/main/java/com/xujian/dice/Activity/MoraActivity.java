package com.xujian.dice.Activity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xujian.dice.BaseActivity.BaseActivity;
import com.xujian.dice.R;

import java.util.Random;

/**
 *
 * 　　┏┓　　　　┏┓
 * 　┏┛┻━━━━┛┻┓
 * 　┃　　　　　　　　┃
 * 　┃　　　━　　　　┃
 * 　┃　┳┛　┗┳　　┃
 * 　┃　　　　　　　　┃
 * 　┃　　　┻　　　　┃
 * 　┃　　　　　　　　┃
 * 　┗━━┓　　　┏━┛
 * 　　　　┃　　　┃　　　神兽保佑
 * 　　　　┃　　　┃　　　代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 *
 *
 * 作者: Created by: xujian on Date: 16/8/3.
 * 邮箱: xj626361950@163.com
 * com.xujian.dice
 * 猜拳
 */

public class MoraActivity extends BaseActivity{
    private ImageView p1, p2;
    private int number1 = -1;
    private int number2 = -1;
    private long gameTime = 1000;

    @Override
    protected void doHandler(Message msg) {
        super.doHandler(msg);
        switch (msg.what) {
            case 10://切换图片
                changeImg();
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mora;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void findView() {
        p1 = (ImageView) findViewById(R.id.p1);
        p2 = (ImageView) findViewById(R.id.p2);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void ViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                start();
                break;
        }
    }

    private void start() {
        new Thread(game).start();
        gameTime = 1000;
    }

    //图片切换动作
    private Runnable game = new Runnable() {
        @Override
        public void run() {
            while (gameTime >= 50) {
                uiHandler.obtainMessage(10).sendToTarget();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameTime = gameTime - 50;
            }

        }
    };

    //p1,p2的显示
    private void changeImg() {
        getRandomNumber();
        if (number1 == 1) {
            p1.setBackgroundResource(R.drawable.jd);
        } else if (number1 == 2) {
            p1.setBackgroundResource(R.drawable.st);
        } else if (number1 == 3) {
            p1.setBackgroundResource(R.drawable.bu);
        }
        if (number2 == 1) {
            p2.setBackgroundResource(R.drawable.zjd);
        } else if (number2 == 2) {
            p2.setBackgroundResource(R.drawable.zst);
        } else if (number2 == 3) {
            p2.setBackgroundResource(R.drawable.zbu);
        }
    }

    //产生p1,p2用户随机数
    private void getRandomNumber() {
        Random r = new Random();
        number1 = Math.abs(r.nextInt(3) + 1);
        number2 = Math.abs(r.nextInt(3) + 1);
    }
}
