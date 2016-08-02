package com.example.XApplication;

import android.content.Intent;

import com.example.BaseActivity.BaseApplication;
import com.example.Service.BackgroundService;
import com.xj.frescolib.Config.FrescoHelper;

/**
 * Created by xujian on 16/2/20.
 * xApplication
 */
public class xApplication extends BaseApplication {
    private static final boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        startBackgroudService();
        //初始化fresco配置
        FrescoHelper.frescoInit(this);
    }

    private void startBackgroudService() {
        Intent i = new Intent(this, BackgroundService.class);
        startService(i);
    }
}
