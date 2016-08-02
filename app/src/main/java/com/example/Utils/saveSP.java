package com.example.Utils;

import android.content.Context;

import com.xj.utils.utils.SharedPreferencesUtil;

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
 * SharedPreferences 业务层调用
 */

public class saveSP {
    private static final String PREFERENCES_SOUNDSTATE = "SOUND_KEY";

    private static saveSP mInstance;
    private static SharedPreferencesUtil sp;

    public synchronized static saveSP getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new saveSP(context.getApplicationContext());
        }
        return mInstance;
    }

    public saveSP(Context context) {
        if (sp == null) {
            sp = SharedPreferencesUtil.getInstance(context.getApplicationContext());
        }
    }

    public boolean getSoundState(boolean defValue) {
        return sp.getBoolean(PREFERENCES_SOUNDSTATE, defValue);
    }

    public void saveSoundState(boolean state) {
        sp.putBoolean(PREFERENCES_SOUNDSTATE, state);
    }
}
