package com.example.MainActivity.Material;

import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.utils.ToastUtils;

/**
 * Created by xujian on 16/4/1.
 * menu 这里最主要的属性就是 actionProviderClass，此属性用于指定ActionProvider。
 *
 * 延后研究 ActionProvider和actionViewClass
 *
 */
public class MyActionProvide extends ActionProvider{
    private Context mcontext;
    /**
     * Creates a new instance. ActionProvider classes should always implement a
     * constructor that takes a single Context parameter for inflating from menu XML.
     *
     * @param context Context for accessing resources.
     */
    public MyActionProvide(Context context) {
        super(context);
        this.mcontext = context;
    }

    //创建CreateAction 布局界面
    @Override
    public View onCreateActionView() {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.action_provider,null,false);
        Button btn = (Button)view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(mcontext,"你好 ActionView");
            }
        });
        return view;
    }
}
