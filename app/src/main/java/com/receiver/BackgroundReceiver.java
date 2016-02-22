package com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.Service.BackgroundService;
import com.Utils.DebugLogs;


/**
 * Created by xujian on 15/8/27.
 * 监听系统启动状体 启动服务
 */
public class BackgroundReceiver extends BroadcastReceiver {
    public final String MANUAL_ACTION = "com.willprojeck.okhttp.okhttp_text.manual";//需要与MainFest.xml中 Receiver同步声明

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
            DebugLogs.v("BackgroundReceiver开机自起启动");
            context.startService(new Intent(context, BackgroundService.class));
        }else if (action.equals(Intent.ACTION_SHUTDOWN)){
            //处理系统停止时候是否结束service
        }else if (action.equals(MANUAL_ACTION)){
            DebugLogs.v("BackgroundReceiver手动启动");
            context.startService(new Intent(context, BackgroundService.class));
        }

    }
}
