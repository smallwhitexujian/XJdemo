package com.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xj.frescolib.View.FrescoDrawee;
import com.xj.frescolib.View.FrescoRoundView;

/**
 * Created by xujian on 2015/9/9.
 * adapter ViewHolder
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置文字的颜色
     */
    public void setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
    }

    /**
     * 设置Button 文字
     */
    public ViewHolder setButtonText(int viewId, CharSequence text) {
        Button view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * frescoDrawView
     */
    public ViewHolder setFrescoDrawView(int viewId, String url) {
        FrescoDrawee view = getView(viewId);
        view.setImageURI(url);
        return this;
    }

    /**
     * FrescoRoundView
     */
    public ViewHolder setFrescoRoundView(int viewId, String url) {
        FrescoRoundView view = getView(viewId);
        view.setImageURI(url);
        return this;
    }

    /**
     * 设置布局隐藏
     */
    public void hideView(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
    }

    /**
     * 设置界面显示
     */
    public void showView(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 设置界面属性
     */
    public void setLayoutParams(int viewId, ViewGroup.LayoutParams lp) {
        View view = getView(viewId);
        view.setLayoutParams(lp);
    }

    /**
     * 设置布局 alpha
     */
    public void setAlpha(int viewId, float alpha) {
        View view = getView(viewId);
        view.setAlpha(alpha);
    }

    /**
     * 设置布局 BackgroundColor
     */
    public void setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
    }

    /**
     * 设置布局 setBackground
     */
    public void setBackground(int viewId, int rsId) {
        View view = getView(viewId);
        view.setBackgroundResource(rsId);
    }

    /**
     * 设置布局 OnClick事件
     */
    public void setOnClick(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }

    /**
     * 获取position
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置postRunnable
     */
    public void postRunnable(int viewId, Runnable action) {
        final View view = getView(viewId);
        view.post(action);
    }

    /**
     * 设置Enable
     */
    public void setEnable(int viewId, boolean enable) {
        final View view = getView(viewId);
        view.setEnabled(enable);
    }

}
