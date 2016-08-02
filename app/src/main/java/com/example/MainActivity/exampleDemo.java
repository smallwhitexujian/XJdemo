package com.example.MainActivity;

import android.view.View;

import com.example.BaseActivity.BaseActivity;
import com.example.Fragment.LabelFragment;
import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.utils.ToastUtils;

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
 * 作者: Created by: xujian on Date: 16/8/2.
 * 邮箱: xj626361950@163.com
 * com.example.MainActivity
 */

public class exampleDemo extends BaseActivity {
    private  LabelFragment labelFragment;
    private int[] career = {
            R.string.cooking,
            R.string.dancing,
            R.string.fashion,
            R.string.food,
            R.string.football,
            R.string.game,
            R.string.movies,
            R.string.music,
            R.string.party,
            R.string.technology,
            R.string.reading,
            R.string.singing,
            R.string.sports,
            R.string.TVProgram
    };
    @Override
    protected int getContentViewId() {
        return R.layout.activity_demo;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }


    @Override
    public void findView() {
        labelFragment = (LabelFragment) getSupportFragmentManager().findFragmentById(R.id.label);
        addFragment(labelFragment);
    }

    @Override
    public void initData() {
        labelFragment.setTags(career, new LabelFragment.CallBack() {
            @Override
            public void Label(String str) {
                ToastUtils.showToast(exampleDemo.this,str);
            }
        });
    }

    @Override
    public void ViewClick(View v) {

    }
}
