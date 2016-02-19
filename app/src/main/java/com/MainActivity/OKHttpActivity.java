package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Utils.DebugLogs;
import com.willprojeck.okhttp.okhttp_text.R;

import java.io.IOException;

import Http.HttpManager;
import okhttp3.Call;

/**
 * Created by xujian on 16/2/19.
 * Http请求
 */
public class OKHttpActivity extends Activity implements View.OnClickListener {
    private TextView text;
    private Button btn, btn1;
    private String URl = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        text = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                HttpManager.CallBack callBack = new HttpManager.CallBack() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onSuccess(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(result);
                            }
                        });
                        DebugLogs.e("------>" + result);
                    }
                };
                HttpManager.getRequest(URl,null,callBack);
                break;
            case R.id.btn1:
                break;
        }
    }
}
