package com.MainActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.willprojeck.okhttp.okhttp_text.R;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("http://img0.pconline.com.cn/pconline/1305/02/3280617_2.jpg");
    }
}
