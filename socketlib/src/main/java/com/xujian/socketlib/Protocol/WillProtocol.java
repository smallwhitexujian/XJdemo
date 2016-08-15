package com.xujian.socketlib.Protocol;

import com.xujian.socketlib.Utils.ByteUtils;

import java.util.HashMap;
import java.util.Map;

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
 *  协议的并解包
 */

public class WillProtocol extends Protocol {
    private static final int PACK_LEN = 4;          //四字节长度
    private static final int PACK_TYPE_LEN = 4;     //四字节操作码
    public static final String TAG = "WillProtocol";
    public static final String KEY_PROTOCOL_LEN = TAG + "_len";
    public static final String KEY_PROTOCOL_TYPE = TAG + "_type";
    public static final String KEY_PROTOCOL_DATA = TAG + "_data";

    public Map<String, byte[]> parsePack(byte[] pack) {
        //不是一个正常的包
        if (pack == null || pack.length < PACK_LEN + PACK_TYPE_LEN) {
            return null;
        }
        //包头
        Map<String, byte[]> protocolMap = new HashMap<>();
        byte[] lenBuffer = new byte[PACK_LEN];
        System.arraycopy(pack, 0, lenBuffer, 0, PACK_LEN);
        protocolMap.put(KEY_PROTOCOL_LEN, lenBuffer);
        //操作码
        byte[] typeBuffer = new byte[PACK_TYPE_LEN];
        System.arraycopy(pack, PACK_LEN, typeBuffer, 0, PACK_TYPE_LEN);
        protocolMap.put(KEY_PROTOCOL_TYPE, typeBuffer);
        //数据
        int dataLen = ByteUtils.bytes2Int(lenBuffer, 0, ByteUtils.BIG_ENDIAN);
        byte[] dataBuffer = new byte[dataLen - PACK_LEN - PACK_TYPE_LEN];
        System.arraycopy(pack, PACK_LEN + PACK_TYPE_LEN, dataBuffer, 0, dataLen - PACK_LEN - PACK_TYPE_LEN);
        protocolMap.put(KEY_PROTOCOL_DATA, dataBuffer);
        return protocolMap;
    }

    //获取数据包长
    @Override
    public int getDataLen(byte[] pack) {
        int packLen = PACK_LEN;
        int packTypeLen = PACK_TYPE_LEN;
        if (pack == null || pack.length < packLen + packTypeLen) {
            return -1;
        }
        byte[] lenBuffer = new byte[packLen];
        System.arraycopy(pack,0,lenBuffer,0,packLen);
        return ByteUtils.bytes2Int(lenBuffer,0,ByteUtils.BIG_ENDIAN);
    }

    @Override
    public int getHeadlen() {
        return PACK_LEN+PACK_TYPE_LEN;
    }

    @Override
    public byte[] getData(byte[] pack) {
        Map<String,byte[]> packMap = parsePack(pack);
        return packMap.get(KEY_PROTOCOL_DATA);
    }

    @Override
    public int getType(byte[] parcel) {
        Map<String,byte[]> packMap = parsePack(parcel);
        byte[] typeAry = packMap.get(KEY_PROTOCOL_TYPE);
        return ByteUtils.bytes2Int(typeAry, 0, ByteUtils.BIG_ENDIAN);
    }

    //系统消息类型
    public static byte[] sendMessage(int typeValue,String jsonStr){
        int dataLen = PACK_LEN + PACK_TYPE_LEN + jsonStr.getBytes().length;
        byte[] pack = parcel(dataLen, typeValue, jsonStr);
        return pack;
    }

    /**
     * 拼包方法
     * @param packLen   包长
     * @param typeValue 操作码
     * @param jsonStr   json数据
     * @return
     */
    private static byte[] parcel(int packLen, int typeValue, String jsonStr) {
        byte[] parcelAry = new byte[packLen];
        byte[] packLenAry = ByteUtils.INT32_2_INT8(packLen, ByteUtils.BIG_ENDIAN);
        byte[] typeAry = ByteUtils.INT32_2_INT8(typeValue, ByteUtils.BIG_ENDIAN);
        System.arraycopy(packLenAry, 0, parcelAry, 0, PACK_LEN);
        System.arraycopy(typeAry, 0, parcelAry, PACK_LEN, PACK_TYPE_LEN);
        if (jsonStr != null) {
            System.arraycopy(jsonStr.getBytes(), 0, parcelAry, PACK_LEN+PACK_TYPE_LEN, jsonStr.getBytes().length);
        }
        return parcelAry;
    }
}
