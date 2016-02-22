package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.willprojeck.okhttp.okhttp_text.R;

/**
 * Created by xujian on 16/2/20.
 * 播放器
 */
public class Mp3Player extends Activity{
    private SeekBar psBar,SoundseekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
    }
}
