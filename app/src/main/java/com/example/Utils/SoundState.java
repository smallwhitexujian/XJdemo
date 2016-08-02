package com.example.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.BaseActivity.BaseApplication;
import com.xj.utils.utils.DebugLogs;

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
 * 作者: Created by: xujian on Date: 16/8/2.
 * 邮箱: xj626361950@163.com
 * com.example.Utils
 * 监听系统媒体声音的是否开关,监听耳机插拔状态,
 *    <注意事项: 需要监听耳机插拔状态的时候请注册广播/>
 */

public class SoundState {
    private static SoundState instance = null;
    private Context mContext;
    private AudioManager audioManager;
    private HeadsetReceiver headsetReceiver;
    public boolean isSoundOpen = false;                      // 声音开关 false 表示当前声音关闭状态
    private int headsetState = 0;                            // 检测耳机是否有插入状态 0.表示没有插入耳机
    private saveSP sp;

    public static SoundState getInstance() {
        if (instance == null) {
            instance = new SoundState();
        }
        return instance;
    }

    public SoundState() {

    }

    public void init(Context context) {
        this.mContext = context;
        sp = saveSP.getInstance(context);
        getCallPhoneListener();
        initAudioSound();
    }

    //获取手机电话状态
    public void getCallPhoneListener() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    //设置系统声音开关 true 表示禁音状态
    private void setSound(Boolean state) {
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, state);
    }

    private class PhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:// 来电状态
                    setSound(true);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接听状态
                    setSound(true);
                    return;
                case TelephonyManager.CALL_STATE_IDLE:// 挂断后回到空闲状态
                    setSound(false);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取当前音频状态
     */
    private boolean getSoundState(boolean defValue) {
        return sp.getSoundState(defValue);
    }

    private void saveSoundState(boolean state) {
        sp.saveSoundState(state);
    }

    /**
     * 初始化系统声音状态
     */
    protected void initAudioSound() {
        BaseApplication.isSound = getSoundState(false); //获取房间当前声音状态
        setSound(BaseApplication.isSound);              //设置系统媒体音乐状态 true表示禁音
        isSoundOpen = BaseApplication.isSound;
    }

    public boolean getIsSoundOpen() {
        return isSoundOpen;
    }

    /**
     * 设置系统声音开关
     */
    public void setAudioSound() {
        BaseApplication.isSound = getSoundState(false);     //获取房间当前声音状态
        BaseApplication.isSound = !BaseApplication.isSound;
        saveSoundState(BaseApplication.isSound);            //保存设置声音状态
        setSound(BaseApplication.isSound);                  //设置系统媒体音乐状态 true表示禁音
        //声音开启
        isSoundOpen = BaseApplication.isSound;
    }


    /**
     * 耳机状态 检测是否插入耳机
     */
    private HeadsetReceiver.HeadsetCallBack headsetCallBack = new HeadsetReceiver.HeadsetCallBack() {
        @Override
        public void listener(int state) {
            headsetState = state;
            if (BaseApplication.isDebug) {
                DebugLogs.d("检测耳机状态" + headsetState);
            }
        }
    };

    public int getHeadsetState() {
        return headsetState;
    }

    //注册耳机监听
    public void registerHeadsetPlugReceiver() {
        headsetReceiver = new HeadsetReceiver(mContext, headsetCallBack);
        headsetReceiver.registerHeadsetPlugReceiver();
    }

    //取消耳机注册
    public void UnregisterHeadsetPlugReceiver() {
        if (headsetReceiver != null) {
            headsetReceiver.unregisterReceiver();
        }
        setSound(false);
    }
}
