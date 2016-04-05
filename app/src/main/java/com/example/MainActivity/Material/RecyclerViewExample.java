/*
RecyclerView是一种新的视图组，目标是为任何基于适配器的视图提供相似的渲染方式。它被作为ListView和GridView控件的继承者，
在最新的support-V7版本中提供支持。

在开发RecyclerView时充分考虑了扩展性，因此用它可以创建想到的任何种类的的布局。但在使用上也稍微有些不便。
这就是Android——要完成一件事情总不是那么容易。

如果使用RecyclerView，你需要了解以下三个元素：

RecyclerView.Adapter
LayoutManager
ItemAnimator
 */
package com.example.MainActivity.Material;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.willprojeck.okhttp.okhttp_text.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewExample extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> listData = new ArrayList<>();
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initToolbar();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        listData.add("C");
        listData.add("JAVA");
        listData.add("PHP");
        listData.add("ANDROID");
        listData.add("IOS");
        listData.add(".NET");
        listData.add("C#");
        listData.add("PH");
        listData.add("C");
        listData.add("C++");
        mRecyclerView.setAdapter(new RecyclerViewAdapter(listData));
    }

    //初始化toolbar
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("RecyclerViewExample");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {
        private List<String> mList;

        public RecyclerViewAdapter(List<String> data) {
            if (data == null) {
                throw new IllegalArgumentException("model Data must not be null");
            }
            mList = data;
        }

        @Override
        public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
            return new ListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListItemViewHolder holder, int position) {
            holder.context.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            TextView context;
            public ListItemViewHolder(View itemView) {
                super(itemView);
                context = (TextView)itemView.findViewById(R.id.info_text);
            }
        }
    }
}
