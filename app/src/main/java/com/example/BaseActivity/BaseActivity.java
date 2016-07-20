package com.example.BaseActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.Constant;
import com.xj.utils.utils.DebugLogs;

import java.lang.ref.WeakReference;

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
 * 基础BaseActivity公共类
 * <p>
 * (1)两个必须实现的抽象方法，
 * 获取布局文件Layout的resource ID，获取布局文件中Fragment的ID
 * (2)添加fragment：
 * 开启一个事物,替换了当前layout容器中的由getFragmentContentId()标识的fragment。
 * 通过调用 addToBackStack(String tag), replace事务被保存到back stack,
 * 因此用户可以回退事务,并通过按下BACK按键带回前一个fragment，
 * 如果没有调用 addToBackStack(String tag), 那么当事务提交后,
 * 那个fragment会被销毁,并且用户不能导航回到它。
 * 其中参数tag将作为本次加入BackStack的Transaction的标志。
 * commitAllowingStateLoss()，这种提交是允许发生异常时状态值丢失的情况下也能正常提交事物
 * (3)移除fragment：
 * 与addToBackStack()相对应的接口方法是popBackStack()，
 * 调用该方法后会将事务操作插入到FragmentManager的操作队列，轮询到该事务时开始执行。
 * 这里进行了一下判断，获取回退栈中所有事务数量，大于1的时候，执行回退操作，等于1的时候，
 * 代表当前Activity只剩下一个Fragment，直接finish()当前Activity即可
 * (4)监听返回键的返回事件，
 * 当事务数量等于1的时候，直接finish()
 * (5)监听网络如果没有网络不做点按事件
 * (6)界面默认关闭键盘。
 * (7)UiHandler刷新界面,防止使用Handler会出现内存泄露
 * (8)触摸界面关闭键盘
 */
public abstract class BaseActivity extends SlideBackActivity implements View.OnClickListener {
    // 布局文件ID
    protected abstract int getContentViewId();

    // 布局中的Fragment的ID
    protected abstract int getFragmentContentId();

    // 定义一个初始化Activity控件的抽象方法initWidget()；
    public abstract void findView();

    // 定义一个初始化Activity控件的抽象方法initData();
    public abstract void initData();

    // 定义点击事件
    public abstract void ViewClick(View v);


    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (isNetConnected()) {
            ViewClick(v);
        } else {
            DebugLogs.d("===>当前网络不可用");
        }
    }

    /**
     * 检测网络是否连接
     */
    private boolean isNetConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo nInfo = connectivity.getActiveNetworkInfo();
            if (nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED) {
                //do your thing
                return true;
            }
        }
        return false;
    }

    /**
     * UI线程刷新 防止Handler内存泄露
     */
    public UIHandler uiHandler = new UIHandler(this);

    @SuppressLint("HandlerLeak")
    private class UIHandler extends Handler {
        private final WeakReference<BaseActivity> activity;

        UIHandler(BaseActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = this.activity.get();
            if (activity != null) {
                activity.UIDoHandler(msg);
            }
        }
    }

    protected void UIDoHandler(Message msg) {

    }

    /**
     * 强制关闭键盘
     */
    private void closeInputKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<? extends BaseActivity> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据，Intent的Extra key为Constant.EXTRA_NAME.DATA
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Bundle data, @NonNull Class<? extends BaseActivity> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra(Constant.DATA, data);
        intent.setClass(this, targetActivity);
        startActivity(intent);
    }

    /***************************************************************************
     * 打印Activity生命周期
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        closeInputKeyBoard();//界面进入默认关闭键盘
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findView();
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
