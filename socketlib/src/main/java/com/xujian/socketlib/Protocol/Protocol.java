package com.xujian.socketlib.Protocol;

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
 * com.xujian.socketlib.Protocol
 * 协议拼接,
 * 模型 包头,状态码 包体
 */

public abstract class Protocol {
    //数据长度
    public int getDataLen(byte[] pack) {
        return 0;
    }

    //返回协议头的长度
    public int getHeadlen(){
        return 0;
    }

    //数据
    public byte[] getData(byte[] pack){
        return null;
    }

    //状态吗
    public int getType(byte[] parcel){
        return 0;
    }
}

