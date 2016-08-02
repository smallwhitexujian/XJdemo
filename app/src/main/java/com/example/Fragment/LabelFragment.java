package com.example.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.BaseActivity.HintFragment;
import com.example.View.MyViewGroup;
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
 * 作者: Created by: xujian on Date: 16/8/2.
 * 邮箱: xj626361950@163.com
 * com.example.Fragment
 * 流水布局的标签工具类,
 */

public class LabelFragment extends HintFragment{
    private MyViewGroup myViewGroup;
    private int[] tagColorArr = {R.color.tag_color_green, R.color.tag_color_yellow, R.color.tag_color_blue,
            R.color.tag_color_red, R.color.tag_color_purple, R.color.tag_color_orange};

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_label;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view, Bundle savedInstanceState) {
        myViewGroup = (MyViewGroup) view.findViewById(R.id.label);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void ViewClick(View v) {

    }


    /**
     * 设置参数
     * @param str 传入数组
     */
    public void setTags(final int[] str, View.OnClickListener clickListener) {
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int i = 0;
        while (i < str.length) {
            final TextView btn = new TextView(getActivity());
            btn.setText(getString(str[i]));
            btn.setTextSize(18);
            btn.setPadding(5, 5, 5, 5);
            int arrIndex = getRandom();
            // 设置标签style,标签字颜色赋值
            btn.setBackgroundResource(R.color.color_ffffff);
            btn.setTextColor(ContextCompat.getColor(getActivity(),tagColorArr[arrIndex]));
            btn.setOnClickListener(clickListener);
            myViewGroup.addView(btn, param1);
            i++;
        }
    }

    /**
     * 设置参数
     * @param str 传入数组
     * @param callBack 返回当前点击字符串
     */
    public void setTags(final int[] str, final CallBack callBack) {
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int i = 0;
        while (i < str.length) {
            final TextView btn = new TextView(getActivity());
            btn.setText(getString(str[i]));
            btn.setTextSize(18);
            btn.setPadding(5, 5, 5, 5);
            int arrIndex = getRandom();
            // 设置标签style,标签字颜色赋值
            btn.setBackgroundResource(R.color.color_ffffff);
            btn.setTextColor(ContextCompat.getColor(getActivity(),tagColorArr[arrIndex]));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.Label(btn.getText().toString());
                }
            });
            myViewGroup.addView(btn, param1);
            i++;
        }
    }

    public interface CallBack{
        void Label (String str);
    }

    /**
     * 设置参数
     * @param str 传入数组
     * @param callBack 返回当前选中第几个
     */
    public void setTags(final int[] str, final CallBack2 callBack) {
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int i = 0;
        while (i < str.length) {
            final TextView btn = new TextView(getActivity());
            btn.setText(getString(str[i]));
            btn.setTextSize(18);
            btn.setPadding(5, 5, 5, 5);
            int arrIndex = getRandom();
            // 设置标签style,标签字颜色赋值
            btn.setBackgroundResource(R.color.color_ffffff);
            btn.setTextColor(ContextCompat.getColor(getActivity(),tagColorArr[arrIndex]));
            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.Label(finalI);
                }
            });
            myViewGroup.addView(btn, param1);
            i++;
        }
    }

    public interface CallBack2{
        void Label (int l);
    }

    /**
     * 获得0-5的随机数
     */
    private int getRandom() {
        int n;
        do {
            n = (int) (Math.random() * 10);
        } while (n > 5);
        return n;
    }
}
