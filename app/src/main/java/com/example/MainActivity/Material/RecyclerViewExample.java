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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.MainActivity.Material.widget.SpacesItemDecoration;
import com.example.Model.RecyclerModel;
import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.frescolib.View.FrescoDrawee;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class RecyclerViewExample extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    private RecyclerView mRecyclerView;
    private List<RecyclerModel> listData = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    enum Type {
        FadeIn(new FadeInAnimator(new OvershootInterpolator(1f))),
        FadeInDown(new FadeInDownAnimator(new OvershootInterpolator(1f))),
        FadeInUp(new FadeInUpAnimator(new OvershootInterpolator(1f))),
        FadeInLeft(new FadeInLeftAnimator(new OvershootInterpolator(1f))),
        FadeInRight(new FadeInRightAnimator(new OvershootInterpolator(1f))),
        Landing(new LandingAnimator(new OvershootInterpolator(1f))),
        ScaleIn(new ScaleInAnimator(new OvershootInterpolator(1f))),
        ScaleInTop(new ScaleInTopAnimator(new OvershootInterpolator(1f))),
        ScaleInBottom(new ScaleInBottomAnimator(new OvershootInterpolator(1f))),
        ScaleInLeft(new ScaleInLeftAnimator(new OvershootInterpolator(1f))),
        ScaleInRight(new ScaleInRightAnimator(new OvershootInterpolator(1f))),
        FlipInTopX(new FlipInTopXAnimator(new OvershootInterpolator(1f))),
        FlipInBottomX(new FlipInBottomXAnimator(new OvershootInterpolator(1f))),
        FlipInLeftY(new FlipInLeftYAnimator(new OvershootInterpolator(1f))),
        FlipInRightY(new FlipInRightYAnimator(new OvershootInterpolator(1f))),
        SlideInLeft(new SlideInLeftAnimator(new OvershootInterpolator(1f))),
        SlideInRight(new SlideInRightAnimator(new OvershootInterpolator(1f))),
        SlideInDown(new SlideInDownAnimator(new OvershootInterpolator(1f))),
        SlideInUp(new SlideInUpAnimator(new OvershootInterpolator(1f))),
        OvershootInRight(new OvershootInRightAnimator(1.0f)),
        OvershootInLeft(new OvershootInLeftAnimator(1.0f));

        private BaseItemAnimator mAnimator;

        Type(BaseItemAnimator animator) {
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initToolbar();
        makeData();
        int i = getIntent().getIntExtra("GRID", 1);
        switch (i){
            case 1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listView
                break;
            case 2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
                break;
            case 3:
                //设置item之间的间隔
                SpacesItemDecoration decoration=new SpacesItemDecoration(16);
                mRecyclerView.addItemDecoration(decoration);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
                break;
        }
        adapter = new RecyclerViewAdapter(listData);
        mRecyclerView.setAdapter(adapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1f)));
        initSpinner();
    }

    /**
     * 选择动画类型
     */
    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Type type : Type.values()) {
            spinnerAdapter.add(type.name());
        }
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRecyclerView.setItemAnimator(Type.values()[position].getAnimator());
                mRecyclerView.getItemAnimator().setAddDuration(500);
                mRecyclerView.getItemAnimator().setRemoveDuration(500);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void makeData() {
        RecyclerModel model = new RecyclerModel();
        model.title = "C";
        model.imgUrl = "http://f.hiphotos.baidu.com/image/h%3D200/sign=236c94ef2c381f3081198aa999004c67/242dd42a2834349bbe78c852cdea15ce37d3beef.jpg";
        RecyclerModel model1 = new RecyclerModel();
        model1.title = "JAVA";
        model1.imgUrl = "http://h.hiphotos.baidu.com/image/pic/item/d31b0ef41bd5ad6ee22bc23d85cb39dbb7fd3c12.jpg";
        RecyclerModel model2 = new RecyclerModel();
        model2.title = ".NET";
        model2.imgUrl = "http://c.hiphotos.baidu.com/image/pic/item/267f9e2f070828389df77ac3bc99a9014d08f16b.jpg";
        RecyclerModel model3 = new RecyclerModel();
        model3.title = "C++";
        model3.imgUrl = "http://c.hiphotos.baidu.com/image/pic/item/b17eca8065380cd743b2043aa544ad34588281bd.jpg";
        RecyclerModel model4 = new RecyclerModel();
        model4.title = "C#";
        model4.imgUrl = "http://c.hiphotos.baidu.com/image/pic/item/314e251f95cad1c8854f5af07b3e6709c83d5174.jpg";
        RecyclerModel model5 = new RecyclerModel();
        model5.title = "PHP";
        model5.imgUrl = "http://c.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7cad15999a1f61fbe096a63a9be.jpg";
        RecyclerModel model6 = new RecyclerModel();
        model6.title = "IOS";
        model6.imgUrl = "http://b.hiphotos.baidu.com/image/pic/item/960a304e251f95caf03a3461cd177f3e6609521d.jpg";
        RecyclerModel model7 = new RecyclerModel();
        model7.title = "OC";
        model7.imgUrl = "http://d.hiphotos.baidu.com/image/h%3D200/sign=4241e02c86025aafcc3279cbcbecab8d/562c11dfa9ec8a13f075f10cf303918fa1ecc0eb.jpg";
        RecyclerModel model8 = new RecyclerModel();
        model8.title = "GO";
        model8.imgUrl = "http://f.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f735600bfe760e0cf3d6cad6cb.jpg";
        RecyclerModel model9 = new RecyclerModel();
        model9.title = "WEB";
        model9.imgUrl = "http://d.hiphotos.baidu.com/image/pic/item/cb8065380cd7912344a13298a9345982b3b7809d.jpg";
        RecyclerModel model10 = new RecyclerModel();
        model10.title = "LNUX";
        model10.imgUrl = "http://a.hiphotos.baidu.com/image/pic/item/8601a18b87d6277f1ee195d42c381f30e824fc6f.jpg";
        listData.add(model);
        listData.add(model1);
        listData.add(model2);
        listData.add(model3);
        listData.add(model4);
        listData.add(model5);
        listData.add(model6);
        listData.add(model7);
        listData.add(model8);
        listData.add(model9);
        listData.add(model10);
    }

    //初始化toolbar
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RecyclerViewExample");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recycler, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add://添加一项
                RecyclerModel model = new RecyclerModel();
                model.title = "增加的一项";
                model.imgUrl = "http://static.sporttery.cn/images/130517/18-13051G32G3-52.jpg";
                listData.add(1,model);
                adapter.notifyItemInserted(1);
                break;
            case R.id.action_remove://删除一项
                if (listData.size() >1){
                    listData.remove(1);
                    adapter.notifyItemRemoved(1);
                }
                break;
        }
        return true;
    }

    private enum ITEM_TYPE{
        ITEM_TYPE_ONE,
        ITEM_TYPE_TWO
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {
        private List<RecyclerModel> mList;

        public RecyclerViewAdapter(List<RecyclerModel> data) {
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
            holder.context.setText(mList.get(position).title);
            holder.frescoDrawee.setImageURI(mList.get(position).imgUrl);
        }


        @Override
        public int getItemViewType(int position) {
            return position%2==0?ITEM_TYPE.ITEM_TYPE_ONE.ordinal():ITEM_TYPE.ITEM_TYPE_TWO.ordinal();
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            TextView context;
            FrescoDrawee frescoDrawee;

            public ListItemViewHolder(View itemView) {
                super(itemView);
                context = (TextView) itemView.findViewById(R.id.info_text);
                frescoDrawee = (FrescoDrawee) itemView.findViewById(R.id.frescoImage);
            }
        }
    }
}
