package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.willprojeck.okhttp.okhttp_text.R;

import Http.HttpManager;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends Activity {
    private Button btn ;
    private TextView text;
    private String url = "http://apitest.vvago.com/Webservers/Bar/BarInfoEidt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn  = (Button)findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//
//                HttpManager.CallBack callBack = new HttpManager.CallBack() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(String result) {
//                        text2.setText(result);
//                    }
//                };
//                HashMap<String, String> params = new HashMap<>();
//                params.put("userid", "10022");
//                params.put("token", "A0CB62E9C390B43442BFE210302E8187");
//                params.put("barid", "100054");
//                params.put("barname", "aslkdjak");
//                params.put("introduce", "你好");
//                params.put("notice", "测试");
//                params.put("area", "杭州");
//                HttpManager.okRequest(HttpManager.Method.POST,url,callBack,params);


                RequestBody formBody = new FormBody.Builder()
                        .add("userid","10022")
                        .add("token","A0CB62E9C390B43442BFE210302E8187")
                        .add("barid","100054")
                        .add("barname","aslkdjak")
                        .add("introduce","你好")
                        .add("notice","测试")
                        .add("area","杭州")
                        .build();
                text.setText(HttpManager.postRequest(url,formBody));
            }
        });
    }
}
