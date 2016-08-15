package com.xujian.socketlib.CallBack;

import com.xujian.socketlib.Protocol.Protocol;

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

public interface TcpSocketCallback {
    // 业务接口
    void onReceiveParcel(Protocol mProtocol, byte[] pack);   // 收到包时候回调

    void onLostConnect();                   // 丢失链接时候回调

    void onReadTaskFinish();                // 读线程正常在没有丢失连接退出后的回调
}
