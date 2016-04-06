package com.example.MainActivity.Material.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/6
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/6          xujian         ${version}
 * Why & What is modified(修改原因): 创建和实现瀑布流的图片修改边距
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=space;
        }
    }
}
