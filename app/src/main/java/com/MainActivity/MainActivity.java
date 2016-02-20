package com.MainActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.willprojeck.okhttp.okhttp_text.R;


public class MainActivity extends Activity {
    private Button btn ;
    private SimpleDraweeView imgText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgText  = (SimpleDraweeView)findViewById(R.id.imgText);
        Uri uri = Uri.parse("http://img0.pconline.com.cn/pconline/1305/02/3280617_2.jpg");
        imgText.setImageURI(uri);
    }
}
