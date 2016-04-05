package com.example.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.xj.utils.utils.DebugLogs;


/**
 * Created by xujian on 16/2/22.
 * service服务
 */
public class BackgroundService extends Service {
    private MyBinder myBinder = new MyBinder();
    private MediaPlayer mPlayer;

    private int mCurrentState = 0;

    private Handler mBackgroundHander = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
            }
        }
    };

    public class MyBinder extends Binder {
        public BackgroundService getBackgroundService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        DebugLogs.e("后台服务被调用");
    }


    @Override
    public IBinder onBind(Intent intent) {
        DebugLogs.v("BackgroundService---->onBind");
        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        DebugLogs.v("BackgroundService---->onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        DebugLogs.v("BackgroundService---->onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
