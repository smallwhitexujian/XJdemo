package com.xujian.socketlib;

import com.xujian.socketlib.Protocol.Protocol;
import com.xujian.socketlib.Protocol.WillProtocol;

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
 * com.xujian.socketlib
 */

public class SocketManagerlmpl implements SocketManager {
    private TcpSocketConnector mTcpSocketConnector;
    private Protocol mProtocol;
    private SocketConfing mSocketConfing;

    public void setSocketConfig(String host, int port) {
        mSocketConfing = new SocketConfing();
        mSocketConfing.setHost(host);
        mSocketConfing.setPort(port);
    }

    public void setSocketConfig(String host, int port, int timeout) {
        mSocketConfing = new SocketConfing();
        mSocketConfing.setHost(host);
        mSocketConfing.setPort(port);
        mSocketConfing.setTimeout(timeout);
    }

    public void startSocket(String host, int port,TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback) {
        mProtocol = new WillProtocol();
        mSocketConfing = new SocketConfing();
        mSocketConfing.setHost(host);
        mSocketConfing.setPort(port);
        mTcpSocketConnector = new TcpSocketConnectorLmpl();
        mTcpSocketConnector.init(mSocketConfing, mProtocol, socketCallback, connectorCallback);
        mTcpSocketConnector.connect();
    }

    public void startSocket(TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback, SocketConfing mSocketConfing) {
        mProtocol = new WillProtocol();
        mTcpSocketConnector = new TcpSocketConnectorLmpl();
        mTcpSocketConnector.init(mSocketConfing, mProtocol, socketCallback, connectorCallback);
        mTcpSocketConnector.connect();
    }

    public void startSocket(TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback) {
        mProtocol = new WillProtocol();
        mTcpSocketConnector = new TcpSocketConnectorLmpl();
        mTcpSocketConnector.init(mSocketConfing, mProtocol, socketCallback, connectorCallback);
        mTcpSocketConnector.connect();
    }

    public void sendMessage(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTcpSocketConnector.write(data);
            }
        }).start();
    }


    public void stopSocket() {
        if (mTcpSocketConnector != null){
            mTcpSocketConnector.disconnect();
        }
    }

    public void Reconnection(){
        if (mTcpSocketConnector != null){
            mTcpSocketConnector.disconnect();
            mTcpSocketConnector.connect();
        }
    }
}
