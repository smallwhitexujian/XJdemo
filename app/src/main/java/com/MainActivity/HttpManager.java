package com.MainActivity;

import android.os.StrictMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xujian on 16/1/20.
 * okHttp请求方式封装
 * 1,返回值 只会返回code[200..300)之间的请求
 * 2,可以支持body json数据.
 * 3,可以支持get和post同步
 */
public class HttpManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    private static void init() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
    }

    /**
     * post body json数据，get请求
     *
     * @param method   判断模型
     * @param url      请求地址
     * @param callback 反馈结果
     * @param params   参数
     */
    public static void PostRequest(int method, String url, final CallBack callback, Map<String, String> params) {
        init();//Android 2.3提供一个称为严苛模式（StrictMode）的调试特性
        String strUrl = restructureURL(method, url, params);
        List<Map<String, String>> listData = new ArrayList<>();
        listData.add(params);
        String json = null;
        try {
            json = getJson(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        if (json != null) {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(strUrl).post(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        callback.onSuccess(result);
                    }
                }
            });
        }
    }

    //post请求

    /***
     * @param url
     * @return
     */
    public static String postRequest(String url,RequestBody params) {
        init();//Android 2.3提供一个称为严苛模式（StrictMode）的调试特性
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).post(params).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String str = response.body().string();
            return str;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    protected static String restructureURL(int method, String url, Map<String, String> params) {
        if (method == Method.GET) {
            url = url + "?" + encodeParameters(params);
        }
        return url;
    }

    private static String encodeParameters(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            encodedParams.append(entry.getKey());
            encodedParams.append('=');
            encodedParams.append(entry.getValue());
            encodedParams.append('&');
        }
        String result = encodedParams.toString();
        return result.substring(0, result.length() - 1);
    }


    //模型设置
    public interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
    }

    //结果反馈
    public interface CallBack {
        //失败处理
        void onFailure(Call call, IOException e);

        //成功处理
        void onSuccess(String result);
    }

    /**
     * @param list
     * @return _Json（单层）；
     * @throws Exception
     */
    public static String getJson(List<Map<String, String>> list) throws Exception {
        String data;
        if (list != null) {
            data = "";
            for (Map<String, String> map : list) {
                StringBuffer m = new StringBuffer();
                m.append("{");
                for (String key : map.keySet()) {
                    m.append("\"" + key + "\":\"" + map.get(key) + "\",");
                }
                String subString = m.substring(0, m.length() - 1) + "}";
                data += subString + ",";
            }
            data = data.substring(0, data.length() - 1);
        } else {
            data = "";
        }
        return data;
    }
}
