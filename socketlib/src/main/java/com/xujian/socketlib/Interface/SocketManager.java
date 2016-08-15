package com.xujian.socketlib.Interface;

import com.xujian.socketlib.SocketConfing;
import com.xujian.socketlib.CallBack.TcpSocketCallback;
import com.xujian.socketlib.CallBack.TcpSocketConnectorCallback;

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
public interface SocketManager {
    void startSocket(String host, int port, TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback);

    void startSocket(TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback);

    void startSocket(TcpSocketConnectorCallback connectorCallback, TcpSocketCallback socketCallback, SocketConfing mSocketConfing);

    void sendMessage(final byte[] data);

    void stopSocket();

    void Reconnection();

    void setSocketConfig(String host, int port);

    void setSocketConfig(String host, int port, int timeout);


}
