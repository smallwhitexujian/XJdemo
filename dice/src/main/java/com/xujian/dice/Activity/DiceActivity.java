package com.xujian.dice.Activity;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xujian.dice.BaseActivity.BaseActivity;
import com.xujian.dice.R;

import java.util.Random;

/**
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
 * <p>
 * <p>
 * 作者: Created by: xujian on Date: 16/8/3.
 * 邮箱: xj626361950@163.com
 * com.xujian.dice.Activity
 */

public class DiceActivity extends BaseActivity {
    private TextView str;
    private int number1 = -1;
    private long gameTime = 1500;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dice;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void findView() {
        Button btn = (Button) findViewById(R.id.btn);
        str = (TextView) findViewById(R.id.str);
        btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void doHandler(Message msg) {
        super.doHandler(msg);
        switch (msg.what){
            case 1:
                changeImg();
                break;
        }
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
                uiHandler.obtainMessage(1).sendToTarget();
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
            str.setText("1");
        } else if (number1 == 2) {
            str.setText("2");
        } else if (number1 == 3) {
            str.setText("3");
        } else if (number1 == 4) {
            str.setText("4");
        } else if (number1 == 5) {
            str.setText("5");
        } else if (number1 == 6) {
            str.setText("6");
        }

    }

    //随机数
    private void getRandomNumber() {
        Random r = new Random();
        number1 = Math.abs(r.nextInt(6) + 1);
    }
}
