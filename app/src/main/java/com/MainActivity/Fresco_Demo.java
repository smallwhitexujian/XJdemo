package com.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.Adapter.CommonAdapter;
import com.Adapter.ViewHolder;
import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.View.RefreshLayout.SwipyRefreshLayout;
import com.xj.utils.View.RefreshLayout.SwipyRefreshLayoutDirection;

import java.util.ArrayList;


/**
 * Created by xujian on 16/3/23.
 * fresco图片处理
 */
public class Fresco_Demo extends Activity implements SwipyRefreshLayout.OnRefreshListener {
    private CommonAdapter<String> mAdapter = null;
    private ListView listview = null;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        listview = (ListView) findViewById(R.id.list_item);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.pullToRefreshView);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        String url1 = "http://f.hiphotos.baidu.com/image/h%3D200/sign=236c94ef2c381f3081198aa999004c67/242dd42a2834349bbe78c852cdea15ce37d3beef.jpg";
        String url2 = "http://h.hiphotos.baidu.com/image/pic/item/d31b0ef41bd5ad6ee22bc23d85cb39dbb7fd3c12.jpg";
        String url3 = "http://c.hiphotos.baidu.com/image/pic/item/267f9e2f070828389df77ac3bc99a9014d08f16b.jpg";
        String url4 = "http://c.hiphotos.baidu.com/image/pic/item/b17eca8065380cd743b2043aa544ad34588281bd.jpg";
        String url5 = "http://c.hiphotos.baidu.com/image/pic/item/314e251f95cad1c8854f5af07b3e6709c83d5174.jpg";
        String url6 = "http://c.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7cad15999a1f61fbe096a63a9be.jpg";
        String url7 = "http://b.hiphotos.baidu.com/image/pic/item/960a304e251f95caf03a3461cd177f3e6609521d.jpg";
        String url8 = "http://d.hiphotos.baidu.com/image/h%3D200/sign=4241e02c86025aafcc3279cbcbecab8d/562c11dfa9ec8a13f075f10cf303918fa1ecc0eb.jpg";
        String url9 = "http://f.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f735600bfe760e0cf3d6cad6cb.jpg";
        String url0 = "http://d.hiphotos.baidu.com/image/pic/item/cb8065380cd7912344a13298a9345982b3b7809d.jpg";
        String url10 = "http://a.hiphotos.baidu.com/image/pic/item/8601a18b87d6277f1ee195d42c381f30e824fc6f.jpg";
        list.add(url1);
        list.add(url2);
        list.add(url3);
        list.add(url4);
        list.add(url5);
        list.add(url6);
        list.add(url7);
        list.add(url8);
        list.add(url9);
        list.add(url0);
        list.add(url10);
        mAdapter = new CommonAdapter<String>(Fresco_Demo.this, list, R.layout.item_layou) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setFrescoDrawView(R.id.contentPanel, list.get(position));
            }
        };
        listview.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh(final SwipyRefreshLayoutDirection direction) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    mSwipyRefreshLayout.setRefreshing(false);
                } else {
                    mSwipyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
