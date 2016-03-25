package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willprojeck.okhttp.okhttp_text.R;


import java.util.HashMap;

import com.xj.utils.Http.HttpManager;
import com.xj.utils.utils.DebugLogs;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by xujian on 16/2/19.
 * Http请求
 */
public class OKHttpActivity extends Activity implements View.OnClickListener {
    private TextView text;
    private LinearLayout line1;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        text = (TextView) findViewById(R.id.text);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());//设置text可以滚动
        line1 = (LinearLayout) findViewById(R.id.line1);

        params = new HashMap<>();
        params.put("userid", "10022");
        params.put("token", "A0CB62E9C390B43442BFE210302E8187");
        params.put("barid", "100054");
        params.put("barname", "你好");
        params.put("introduce", "你好");
        params.put("notice", "你好");
        params.put("area", "杭州");

        addBtn("get有回调形式请求", 0);
        addBtn("get没有回调直接返回形式", 1);
        addBtn("PostRequest 有回调", 2);
        addBtn("PostRequest有回调形式请求", 3);
        addBtn("PostRequest没有回调", 4);
        addBtn("Post from有回调", 5);
        addBtn("Post from没有回调", 6);

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
    public void onClick(View v) {
        int tag = (int) v.getTag();
        String url;
        HttpManager.CallBack callBack;
        switch (tag) {
            case 0://get有回调的请求形式
                url = "http://apitest.vvago.com/Webservers/Bar/BarIndex";
                DebugLogs.e("----->查看是否执行了。");
                callBack = new HttpManager.CallBack() {
                    @Override
                    public void onSuccess(final String result) {
                        text.setText(result);
                    }
                };
                HttpManager.getRequest(OKHttpActivity.this, url, null, callBack);
                break;
            case 1://无回调形式
                url = "http://apitest.vvago.com/Webservers/Bar/BarInfo";
                text.setText(HttpManager.getRequest(url,null));
                break;

            case 2://get请求
                url = "http://www.fresco-cn.org";
                callBack =   new HttpManager.CallBack() {
                    @Override
                    public void onSuccess(String result) {
                        text.setText(result);
                    }
                };
                HttpManager.Request(this,HttpManager.Method.GET, url, callBack, params);
                break;
            case 3://post 发送的内容包裹在http body中，
                url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
                callBack = new HttpManager.CallBack() {
                    @Override
                    public void onSuccess(String result) {
                        text.setText(result);
                    }
                };
                HttpManager.Request(this,HttpManager.Method.POST, url, callBack, params);
                break;
            case 4://post 发送的内容包裹在http body中，
                url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
                text.setText(HttpManager.Request(url, HttpManager.Method.POST,params));
                break;

            case 5://post from表单提交
                url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
                callBack = new HttpManager.CallBack() {
                    @Override
                    public void onSuccess(String result) {
                        text.setText(result);
                    }
                };
                RequestBody formBody = new FormBody.Builder()
                        .add("userid","10022")
                        .add("token","A0CB62E9C390B43442BFE210302E8187")
                        .add("barid","100054")
                        .add("barname","aslkdjak")
                        .add("introduce","你好")
                        .add("notice","测试")
                        .add("area","杭州")
                        .build();
                HttpManager.postRequest(this, url, formBody, callBack);
                break;
            case 6:
                url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
                RequestBody formBody2 = new FormBody.Builder()
                        .add("userid","10022")
                        .add("token","A0CB62E9C390B43442BFE210302E8187")
                        .add("barid","100054")
                        .add("barname","aslkdjak")
                        .add("introduce","你好")
                        .add("notice","测试")
                        .add("area","杭州")
                        .build();
                text.setText(HttpManager.postRequest(url,formBody2));
                break;
        }
    }
}
