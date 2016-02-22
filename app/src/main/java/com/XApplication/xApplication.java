package com.XApplication;

import android.app.Application;
import android.content.Intent;

import com.Service.BackgroundService;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xujian on 16/2/20.
 * xApplication
 */
public class xApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startBackgroudService();
        Fresco.initialize(this);
    }

    private void startBackgroudService() {
        Intent i = new Intent(this, BackgroundService.class);
        startService(i);
    }
}
