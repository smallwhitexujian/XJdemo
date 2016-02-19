package com.willprojeck.okhttp.okhttp_text;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Marquee.MarqueeUilts;

public class MainActivity extends Activity {
    private Button btn ;
    private String url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn  = (Button)findViewById(R.id.button);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////
////                HttpManager.CallBack callBack = new HttpManager.CallBack() {
////                    @Override
////                    public void onFailure(Call call, IOException e) {
////
////                    }
////
////                    @Override
////                    public void onSuccess(String result) {
////                        text2.setText(result);
////                    }
////                };
////                HashMap<String, String> params = new HashMap<>();
////                params.put("userid", "10022");
////                params.put("token", "A0CB62E9C390B43442BFE210302E8187");
////                params.put("barid", "100054");
////                params.put("barname", "aslkdjak");
////                params.put("introduce", "你好");
////                params.put("notice", "测试");
////                params.put("area", "杭州");
////                HttpManager.okRequest(HttpManager.Method.POST,url,callBack,params);
//
//                HashMap<String, String> params = new HashMap<>();
//                params.put("userid", "10022");
//                params.put("token", "A0CB62E9C390B43442BFE210302E8187");
//                params.put("barid", "100054");
//                params.put("barname", "aslkdjak");
//                params.put("introduce", "你好");
//                params.put("notice", "测试");
//                params.put("area", "杭州");
//                StringBuilder encodedParams = new StringBuilder();
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    encodedParams.append(entry.getKey());
//                    encodedParams.append('=');
//                    encodedParams.append(entry.getValue());
//                    encodedParams.append('&');
//                }
//                String result = encodedParams.toString();
//                result = result.substring(0, result.length() - 1);
//                Log.d("-------->",result );
//                RequestBody formBody = new FormBody.Builder()
//                        .add("userid","10022")
//                        .add("token","A0CB62E9C390B43442BFE210302E8187")
//                        .add("barid","100054")
//                        .add("barname","aslkdjak")
//                        .add("introduce","你好")
//                        .add("notice","测试")
//                        .add("area","杭州")
//                        .build();
//
//
//                text2.setText(HttpManager.postRequest(url,formBody));
//            }
//        });
    }
}
