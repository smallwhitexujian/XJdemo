package com.example.MainActivity.Recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.utils.DebugLogs;

/**
 * Created by yjwfn on 15-11-28.
 */
public class SlideItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemSlideHelper.Callback {
    private RecyclerView mRecyclerView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new TextVH(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String text = "item: " + position;
        TextVH textVH = (TextVH) holder;
        textVH.textView.setText(text);
        ((TextVH) holder).textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLogs.d("-------点击了删除按钮--->"+(position -1));
                notifyItemRemoved(holder.getLayoutPosition());
            }
        });
        textVH.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLogs.d("-------点击了tv--->"+holder.getLayoutPosition());
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }


}

class TextVH extends RecyclerView.ViewHolder {
    TextView textView;
    TextView textView1;

    public TextVH(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv_text);
        textView1 = (TextView) itemView.findViewById(R.id.tv_delete);
    }
}