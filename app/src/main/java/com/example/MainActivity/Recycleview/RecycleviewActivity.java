package com.example.MainActivity.Recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.willprojeck.okhttp.okhttp_text.R;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/26
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/26          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class RecycleviewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LinearItemDecoration(Color.BLACK));
        recyclerView.setAdapter(new SlideItemAdapter());
    }
}