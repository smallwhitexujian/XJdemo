package com.XApplication;

import android.app.Application;
import android.content.Intent;

import com.Service.BackgroundService;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;

import Fresco.Utils.FrescoConfigConstants;
import Fresco.Utils.FrescoHelper;

/**
 * Created by xujian on 16/2/20.
 * xApplication
 */
public class xApplication extends Application {
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
