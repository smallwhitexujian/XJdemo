package com.xujian.socketlib;

import com.xj.utils.utils.DebugLogs;
import com.xujian.socketlib.CallBack.TcpSocketCallback;
import com.xujian.socketlib.Interface.TCPSocket;
import com.xujian.socketlib.Protocol.Protocol;
import com.xujian.socketlib.Utils.ByteArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * socket 连接实体
 */

public class TCPSocketlmpl implements TCPSocket {
    private volatile boolean isRun = true;
    private Socket mSocket;
    private InputStream mInputStream = null;    // 读取数据
    private OutputStream mOutputStream = null;  // 写入数据
    private SocketConfing socketConfing;        // 地址配置
    private Protocol mProtocol;                 // 协议数据
    private int mRunStatus = CONNECTNULL;       // socket 状态
    private boolean isLostConnect = false;      // 是否丢失状态,
    private int MAX_BORDER = 10 * 1024 * 1024;  //最大空间
    private TcpSocketCallback mTcpSocketCallback; //业务回调
    private ExecutorService mPool = Executors.newFixedThreadPool(2);
    private boolean isReconnection = true;

    TCPSocketlmpl(SocketConfing confing, Protocol protocol, TcpSocketCallback tcpSocketCallback) {
        mRunStatus = CONNECTINIT;
        this.socketConfing = confing;
        this.mProtocol = protocol;
        this.mTcpSocketCallback = tcpSocketCallback;
    }

    public boolean isReconnection() {
        return isReconnection;
    }

    public void setReconnection(boolean reconnection) {
        isReconnection = reconnection;
    }

    //创建socket连接 创建输入流和写入流
    @Override
    public boolean connect() {
        try {
            mRunStatus = CONNECTING;
            mSocket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(socketConfing.getHost(), socketConfing.getPort());
            mSocket.connect(inetSocketAddress, socketConfing.getTimeout());
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();
            mRunStatus = CONNECTED;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            mRunStatus = CONNECTFAILD;
        }
        return false;
    }

    //关闭连接
    public void disconnect() {
        try {
            if (mInputStream != null) {
                mInputStream.close();
                mInputStream = null;
            }
            if (mOutputStream != null) {
                mOutputStream.close();
                mOutputStream = null;
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
            isRun = false;
            mRunStatus = CONNECTLOST;
        } catch (IOException e) {
            e.printStackTrace();
            mRunStatus = CONNECTCLOSE;
        }
    }

    //获取当前连接状态,
    public int getRunStatus() {
        return mRunStatus;
    }


    /**
     * @param b   数据
     * @param off 指定字节数组
     * @param len 长度
     * @return //发送数据出去
     */
    public boolean send(byte[] b, int off, int len) {
        try {
            if (mOutputStream != null) {
                mOutputStream.write(b, off, len);
                return true;
            }
        } catch (IOException e) {
            isLostConnect = true;
            onLostConnect();
            e.printStackTrace();
        }
        return false;
    }

    public void writeHeartbeat(byte[] heartParcel) {
        if (mOutputStream != null) {
            try {
                mOutputStream.write(heartParcel, 0, heartParcel.length);
            } catch (IOException e) {
                onLostConnect();
                e.printStackTrace();
            }
        }
    }

    //丢失连接
    private void onLostConnect() {
        disconnect();
        if (mTcpSocketCallback != null) {
            mTcpSocketCallback.onLostConnect(isReconnection());
        }
    }

    //读取socket
    public void read() {
//        try {
//            while (true) {
//                DataInputStream input = new DataInputStream(mInputStream);
//                byte[] buffer;
//                buffer = new byte[input.available()];
//                if (buffer.length != 0) {
//                    // 读取缓冲区
//                    input.read(buffer);
//                    String msg = new String(buffer, "UTF-8");//注意转码，不然中文会乱码。
//                    DebugLogs.d("----->" + msg);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (mInputStream == null) {
            return;
        }
        mPool.execute(new Runnable() {
            @Override
            public void run() {
                ByteArrayBuffer mByteBuffer = null;
                isRun = true;
                isLostConnect = false;
                setReconnection(true);
                while (isRun) {
                    try {
                        if (mByteBuffer == null) {
                            mByteBuffer = new ByteArrayBuffer(mProtocol.getHeadlen());
                        }
                        int count = mByteBuffer.mlen;
                        int readCount = mByteBuffer.moffset;// 已经成功读取的字节的个数
                        while (readCount < mByteBuffer.mlen) {
                            int readNum = mInputStream.read(mByteBuffer.mbuffer, readCount, count - readCount);
                            if (readNum < 0) {
                                DebugLogs.d("socket ---lost connection ");
                                isRun = false;
                                isLostConnect = true;
                                break;
                            }
                            if (readNum > 0) {
                                readCount += readNum;
                                mByteBuffer.flush(readCount);
                            }
                        }
                        int totalLen = mProtocol.getDataLen(mByteBuffer.mbuffer);
                        //数据包小于指定大小,丢失连接（inputsream关闭)
                        if (mByteBuffer.mlen < mByteBuffer.moffset) {
                            isRun = false;
                            mByteBuffer = null;
                            isLostConnect = true;
                        } else {
                            if (mByteBuffer.mlen == totalLen) {
                                if (mTcpSocketCallback != null) {
                                    mTcpSocketCallback.onReceiveParcel(mProtocol, mByteBuffer.mbuffer);
                                }
                                mByteBuffer = null;
                            } else {
                                if (mByteBuffer.mlen < MAX_BORDER && totalLen < MAX_BORDER) {
                                    mByteBuffer.flushSize(totalLen, mByteBuffer);
                                } else {//oom异常处理
                                    isRun = false;
                                    mByteBuffer = null;
                                    isLostConnect = true;
                                }
                            }
                        }
                    } catch (IOException e) {
                        DebugLogs.i("ioException");
                        if (e instanceof java.net.SocketTimeoutException) {
                            continue;
                        }
                        isLostConnect = true;
                        isRun = false;
                        e.printStackTrace();
                    }
                }
                disconnect();
                //丢失连接业务流程回调
                if (isLostConnect) {
                    if (mTcpSocketCallback != null) {
                        mTcpSocketCallback.onLostConnect(isReconnection());
                    }
                } else {
                    if (mTcpSocketCallback != null) {
                        mTcpSocketCallback.onReadTaskFinish();
                    }
                }
            }
        });
    }
}
