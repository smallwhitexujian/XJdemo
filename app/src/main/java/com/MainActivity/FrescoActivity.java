package com.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.willprojeck.okhttp.okhttp_text.R;


/**
 * Created by xujian on 16/2/29.
 * 图片处理
 */
public class FrescoActivity extends Activity implements View.OnClickListener{
    private LinearLayout line1;
    private SimpleDraweeView draweeView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_fresco);
        line1 = (LinearLayout) findViewById(R.id.line1);
        draweeView = (SimpleDraweeView) findViewById(R.id.draweeView);
        Uri uri = Uri.parse("http://img0.pconline.com.cn/pconline/1305/02/3280617_2.jpg");
        draweeView.setImageURI(uri);

        addBtn("gif listView", 0);
        addBtn("draweeView",1);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Intent i;
        switch (tag){
            case 0:
                i = new Intent(FrescoActivity.this,Fresco_gif_listview.class);
                startActivity(i);
                break;
            case 1:

                break;
        }
    }
}
