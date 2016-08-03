package com.xujian.dice.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

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
 * 作者: Created by: xujian on Date: 16/7/15.
 * 邮箱: xj626361950@163.com
 * com.example.MainActivity
 */
abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private BaseActivity mActivity;

    //获取布局文件ID
    protected abstract int getLayoutId();

    // 定义一个初始化Activity控件的抽象方法initWidget()；
    protected abstract void initView(LayoutInflater inflater ,View view, Bundle savedInstanceState);

    // 定义一个初始化Activity控件的抽象方法initData();
    protected abstract void initData();

    // 定义点击事件
    protected abstract void ViewClick(View v);



    public FragmentHandler fragmentHandler = new FragmentHandler(this);

    private static class FragmentHandler extends Handler {
        private final WeakReference<BaseFragment> weakFragment;

        FragmentHandler(BaseFragment fragment) {
            this.weakFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment fragment = this.weakFragment.get();
            if (fragment != null) {
                fragment.doFragmentHandler(msg);
            }
        }
    }

    private void doFragmentHandler(Message msg) {

    }


    //获取宿主Activity
    private BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }


    @Override
    public void onClick(View v) {
        ViewClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(inflater,view, savedInstanceState);
        initData();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override//repair bug:java.lang.IllegalStateException: Activity has been destroyed
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
