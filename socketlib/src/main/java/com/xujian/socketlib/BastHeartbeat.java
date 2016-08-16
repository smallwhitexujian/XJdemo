package com.xujian.socketlib;

import com.xujian.socketlib.Interface.TCPSocket;
import com.xujian.socketlib.Utils.DebugLogs;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
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
 * <p>
 * <p>
 * 作者: Created by: xujian on Date: 16/8/16.
 * 邮箱: xj626361950@163.com
 * com.xujian.socketlib
 * 心跳包
 */

public class BastHeartbeat implements Headerbeat {
    private Timer mTimer;
    private TimerTask mTimerTask;
    private volatile boolean isRun = false;
    private int DELAY_TIME = 2000;//首次启动的时间
    private int PERIOD_TIME = 20000;//间隔时间


    public void setConfig(int firstTime, int period) {
        this.DELAY_TIME = firstTime;
        this.PERIOD_TIME = period;
    }

    public void doHeartbeat(final TCPSocket tcpSocket, final byte[] heartbeatParcel) {
        if (tcpSocket == null || heartbeatParcel == null) {
            DebugLogs.e(tcpSocket + "Room  doHeartbeat faild " + Arrays.toString(heartbeatParcel));
            return;
        }
        if (isRun) {
            return;
        }
        isRun = true;
        closeTimerTask();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {
                try {
                    tcpSocket.writeHeartbeat(heartbeatParcel);
                } catch (Exception e) {
                    e.printStackTrace();
                    doneHeartbeat();
                }
            }
        };
        mTimer.schedule(mTimerTask, DELAY_TIME, PERIOD_TIME);
    }


    private void closeTimerTask() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public void doneHeartbeat() {
        closeTimerTask();
        isRun = false;
    }

}
