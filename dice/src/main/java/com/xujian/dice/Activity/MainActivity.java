package com.xujian.dice.Activity;

import android.view.View;
import android.widget.Button;

import com.xujian.dice.BaseActivity.BaseActivity;
import com.xujian.dice.R;

public class MainActivity extends BaseActivity {
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }


    @Override
    public void findView() {
        Button btn = (Button) findViewById(R.id.btn);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void ViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                startActivity(MoraActivity.class);
                break;
            case R.id.btn2:
                startActivity(DiceActivity.class);
                break;
        }
    }
}
