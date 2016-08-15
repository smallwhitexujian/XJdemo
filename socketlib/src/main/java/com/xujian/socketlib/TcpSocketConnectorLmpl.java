package com.xujian.socketlib;

import android.util.Log;

import com.xujian.socketlib.CallBack.TcpSocketCallback;
import com.xujian.socketlib.CallBack.TcpSocketConnectorCallback;
import com.xujian.socketlib.Interface.TCPSocket;
import com.xujian.socketlib.Interface.TcpSocketConnector;
import com.xujian.socketlib.Protocol.Protocol;

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
 * 作者: Created by: xujian on Date: 16/8/15.
 * 邮箱: xj626361950@163.com
 * PACKAGE_NAME
 * 连接服务器处理
 *    主要处理服务器的初始化,服务器的重连以及写入
 */

public class TcpSocketConnectorLmpl implements TcpSocketConnector {
    private static String TAG = "TcpSocketConnector";
    private int connectTime = 0;    //连接次数
    private int MAX_RETRAY_TIME = 5;//重连次数
    private int DELAY_TIME = 1000;//定时器延时启动
    private int PERIDO = 3000;//定时器定时周期
    private volatile boolean isRun = false; //方式线程多次启动
    private Timer mTimer;
    private TimerTask mTimerTask;

    private TCPSocket mSocket;
    private SocketConfing confing;
    private Protocol mProtocol;
    private TcpSocketCallback tcpsocketCallback;
    private TcpSocketConnectorCallback connectorCallback;

    public void init(SocketConfing socketConfing, Protocol protocol, TcpSocketCallback tcpSocketCallback, TcpSocketConnectorCallback tcpSocketConnectorCallback) {
        if (socketConfing == null) {
            throw new RuntimeException("socketConfing null point exception ");
        }
        if (tcpSocketCallback == null) {
            throw new RuntimeException("tcpSocketCallback null pont exception ");
        }
        if (tcpSocketConnectorCallback == null) {
            throw new RuntimeException("tcpSocketConnectorCallback null pont exception ");
        }
        this.confing = socketConfing;
        this.mProtocol = protocol;
        this.tcpsocketCallback = tcpSocketCallback;
        this.connectorCallback = tcpSocketConnectorCallback;
    }
    //连接服务器
    public void connect() {
        if (isRun) {
            return;
        }
        isRun = true;//防止启动多次
        mSocket = new TCPSocketlmpl(confing, mProtocol, tcpsocketCallback);//创建socket实体
        stopTimer();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (connectTime++ < MAX_RETRAY_TIME) {//最大连接次数
                    if (mSocket.connect()) {
                        Log.d(TAG,"服务器已连接..开始接受数据..");
                        stopTimer();
                        mSocket.read();//读取数据流
                        connectorCallback.connectSuc();//通知连接成功
                    } else {
                        connectorCallback.connectFaild();
                    }
                } else {
                    stopTimer();
                    connectorCallback.retryOverlimit(connectTime);
                }
            }
        };
        mTimer.schedule(mTimerTask, DELAY_TIME, PERIDO);
    }

    //向服务器发送消息
    public boolean write(byte[] data) {
        try {
            if (mSocket.getRunStatus() == TCPSocket.CONNECTED) {//如果是连接状态就发送消息
                return mSocket.send(data, 0, data.length);
            }else{
                mSocket.disconnect();
                connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void disconnect() {
        if (mSocket != null){
            mSocket.disconnect();
        }
    }

    //断开连接
    private void stopTimer() {
        closeTimerTask();
        restore();
    }


    //关闭计时器
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

    //重置参数
    private void restore() {
        mTimer = null;
        connectTime = 0;
        isRun = false;
    }
}
