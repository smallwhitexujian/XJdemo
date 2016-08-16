package com.xujian.socketlib.Interface;

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
 * 作者: Created by: xujian on Date: 16/8/5.
 * 邮箱: xj626361950@163.com
 * com.xujian.socketlib
 */

public interface TCPSocket {
    int CONNECTNULL = -1;   // 原始状态
    int CONNECTLOST = -2;   // 丢失连接
    int CONNECTCLOSE = -3;  // socket关闭
    int CONNECTFAILD = 0;   // 连接失败
    int CONNECTINIT = 1;    // 初始化中....
    int CONNECTING = 2;     // 连接中
    int CONNECTED = 3;      // 连接成功

    boolean connect();      // socket连接

    void disconnect();      // 断开连接

    boolean send(byte[] b, int off, int len);//发送消息

    void read();            // 读取服务器数据

    int getRunStatus();     // 获取状态连接

    boolean isReconnection();

    void setReconnection(boolean reconnection);
}
