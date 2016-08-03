package com.xujian.dice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView p1, p2;
    public int number1 = -1;
    public int number2 = -1;
    private long gameTime = 1000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10://切换图片
                    changeImg();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p1 = (ImageView) findViewById(R.id.p1);
        p2 = (ImageView) findViewById(R.id.p2);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
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
                handler.sendEmptyMessage(10);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameTime = gameTime - 50;
            }

        }
    };

    private void changeImg() {
        getRandomNumber();
        if (number1 == 1) {
            p1.setBackgroundResource(R.drawable.jd);
        }else if (number1 == 2){
            p1.setBackgroundResource(R.drawable.st);
        }else if (number1 == 3){
            p1.setBackgroundResource(R.drawable.bu);
        }
        if (number2 == 1) {
            p2.setBackgroundResource(R.drawable.zjd);
        }else if (number2 == 2){
            p2.setBackgroundResource(R.drawable.zst);
        }else if (number2 == 3){
            p2.setBackgroundResource(R.drawable.zbu);
        }
    }

    private void getRandomNumber() {
        Random r = new Random();
        number1 = Math.abs(r.nextInt(3) + 1);
        number2 = Math.abs(r.nextInt(3) + 1);
    }
}
