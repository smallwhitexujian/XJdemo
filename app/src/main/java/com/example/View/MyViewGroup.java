package com.example.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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
 * com.example.View
 * 流水布局
 */

public class MyViewGroup extends ViewGroup {
    private final static int VIEW_MARGIN = 40;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int index = 0;index <getChildCount();index++){
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        t = 10;
        final int count = getChildCount();
        int row = 0;//行数
        int lengthX = l;
        int lengthY = t;
        for (int i = 0;i<count ;i++){
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width +VIEW_MARGIN;
            lengthY = row * (height + VIEW_MARGIN ) + VIEW_MARGIN +height +t;
            if (lengthX > r){
                lengthX = width + VIEW_MARGIN + l;
                row++;
                lengthY = row * (height  + VIEW_MARGIN) +VIEW_MARGIN + height + t;
            }
            child.layout(lengthX - width , lengthY - height ,lengthX ,lengthY);
        }
    }
}