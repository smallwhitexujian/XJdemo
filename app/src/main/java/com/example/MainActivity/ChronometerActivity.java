package com.example.MainActivity;

import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.BaseActivity.BaseActivity;
import com.willprojeck.okhttp.okhttp_text.R;

/**
 *
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
 *
 *
 * 作者: Created by: xujian on Date: 16/9/9.
 * 邮箱: xj626361950@163.com
 * com.example.MainActivity
 */

public class ChronometerActivity extends BaseActivity {
    private Chronometer timer;
    private Button btnStart,btnEnd,btnReset;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_chronometer;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void findView() {
        // 获得计时器对象
        timer = (Chronometer)this.findViewById(R.id.chronometer);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnEnd = (Button)findViewById(R.id.btnEnd);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void ViewClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                // 将计时器清零
//                timer.setBase(SystemClock.elapsedRealtime());
                //开始计时
                timer.start();
                break;
            case R.id.btnEnd:
                //停止计时
                timer.stop();
                break;
            case R.id.btnReset:
                timer.setBase(SystemClock.elapsedRealtime());//将计时器清零
                break;
        }
    }
}
