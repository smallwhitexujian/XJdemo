package com.xujian.socketlib.Utils;

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
 * com.xujian.socketlib.Utils
 * 数组
 */

public class ByteArrayBuffer {
    public byte[] mbuffer;  //新字节组
    public int moffset;     //已经成功读取的字节的个数
    public int mlen;        //包长
    public int mrealsize;   //计数大小

    public ByteArrayBuffer(int len) {
        if (len < 0) {
            throw new IllegalArgumentException();
        }
        mbuffer = new byte[len];
        moffset = 0;
        mlen = len;
        mrealsize = 0;
    }

    public ByteArrayBuffer(int len, ByteArrayBuffer oldBuffer) {
        if (oldBuffer == null || oldBuffer.mbuffer == null || len < 0) {
            throw new IllegalArgumentException();
        }
        int size = len + oldBuffer.mlen;
        mbuffer = new byte[size];
        System.arraycopy(oldBuffer.mbuffer, 0, mbuffer, 0, oldBuffer.mlen);
        moffset = len;
        mrealsize = len;
        mlen = size;
    }

    public int append(byte[] data) {
        if (data == null) {
            return -1;
        }
        System.arraycopy(data, 0, mbuffer, moffset, data.length);
        moffset += data.length;
        mrealsize = moffset;
        return moffset;
    }

    public ByteArrayBuffer append(int readLean,ByteArrayBuffer oldBuffer){
        ByteArrayBuffer newBuffer = new ByteArrayBuffer(oldBuffer.mlen+readLean);
        System.arraycopy(oldBuffer.mbuffer, 0, newBuffer.mbuffer, 0, oldBuffer.moffset);
        newBuffer.moffset = oldBuffer.moffset;
        newBuffer.mrealsize = oldBuffer.mrealsize;
        return newBuffer;
    }

    //刷新接受到的包
    public int flush(int position) {
        moffset = position;
        return moffset;
    }

    //备份一份数据
    public void flushSize(int totalSize, ByteArrayBuffer buffer) {
        byte[] data = buffer.mbuffer;
        buffer.mbuffer = new byte[totalSize];
        System.arraycopy(data, 0, buffer.mbuffer, 0, buffer.moffset);
        buffer.mlen = totalSize;
    }
}
