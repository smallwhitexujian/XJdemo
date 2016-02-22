package com.MainActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.Service.BackgroundService;
import com.Utils.DebugLogs;

/**
 * Created by xujian on 16/2/22.
 * 服务器绑定协议
 */
public class BinderActivity extends Activity {
    protected BackgroundService bindService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            DebugLogs.i("Service----->onServiceDisconnected()");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundService.MyBinder binder = (BackgroundService.MyBinder) service;
            bindService = binder.getBackgroundService();
        }

    };

    /**
     * 解绑
     */
    private void unBindService() {
        BinderActivity.this.unbindService(conn);
    }

    private void bindService() {
        Intent intent = new Intent(BinderActivity.this, BackgroundService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBindService();

    }
}
