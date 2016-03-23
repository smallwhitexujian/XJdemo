package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;

import com.willprojeck.okhttp.okhttp_text.R;

import Fresco.Utils.View.FrescoDrawee;
import Fresco.Utils.View.FrescoRoundView;

/**
 * Created by xujian on 16/3/23.
 * fresco图片处理
 */
public class Fresco_Demo extends Activity{
    private FrescoDrawee img ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        img = (FrescoDrawee)findViewById(R.id.img);
        String url2 =("http://wx.qlogo.cn/mmopen/H3HiaORN4mx6eT5oJG7E9EVN4Pn2b91odmXxaiageBqJynPZac7gj5TypM4yX5l6taJC0WcnsdpaxzImt1SFIW6n8XIOwRrbxC/0");
        String url = "http://img32.mtime.cn/up/2013/07/20/142329.63833494_500.jpg";
        String url3 = "http://img3.imgtn.bdimg.com/it/u=3432266815,1060385898&fm=21&gp=0.jpg";
        img.setImageURI(url3,url2);

        FrescoRoundView frescoRoundView = (FrescoRoundView)findViewById(R.id.roundImage);
        frescoRoundView.setImageURI(url);
    }
}
