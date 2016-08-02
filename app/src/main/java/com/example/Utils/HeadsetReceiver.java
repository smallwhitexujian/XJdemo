package com.example.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 *
 * 　　┏┓　　　　┏┓
 * 　┏┛┻━━━━┛┻┓
 * 　┃　　　　　　　　┃
 * 　┃　　　━　　　　┃
 * 　┃　┳┛　┗┳　　┃
 * 　┃　　　　　　　　┃
 * 　┃　　　┻　　　　┃
 * 　┃　　　　　　　　┃
 * 　┗━━┓　　　┏━┛
 * 　　　　┃　　　┃　　　神兽保佑
 * 　　　　┃　　　┃　　　代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 *
 *
 * 作者: Created by: xujian on Date: 16/8/2.
 * 邮箱: xj626361950@163.com
 * com.example.Utils
 */

public class HeadsetReceiver extends BroadcastReceiver {
    private HeadsetCallBack headsetCallBack;
    private Context context;
    private boolean bindState = false;

    public HeadsetReceiver(Context context, HeadsetCallBack headsetCallBack) {
        this.context = context;
        this.headsetCallBack = headsetCallBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("state")) {
            if (headsetCallBack != null) {
                headsetCallBack.listener(intent.getIntExtra("state", 0));
            }
        }
    }

    public void registerHeadsetPlugReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.registerReceiver(this, filter);
        bindState = true;
    }

    public void unregisterReceiver() {
        if (bindState) {
            context.unregisterReceiver(this);
            bindState = false;
        }
    }

    public interface HeadsetCallBack {
        void listener(int state);
    }
}
