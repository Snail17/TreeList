package com.xiaohong.treelist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.xiaohong.treelist.BR;
import com.xiaohong.treelist.adapter.ExpandTreeListAdapter;
import com.xiaohong.treelist.bean.FileBean;
import com.xiaohong.treelist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_EXPAND = 1;
    private ListView mListView;
    private ExpandTreeListAdapter mAdapter;
    private ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    public void initData() {
        List<FileBean> list = new ArrayList<>();
        list.add(new FileBean("1", "0", "姓氏", "1"));
        list.add(new FileBean("01", "1", "周", "2"));
        list.add(new FileBean("02", "1", "王", "2"));
        list.add(new FileBean("03", "1", "理", "2"));
        list.add(new FileBean("2", "0", "名字", "1"));
        list.add(new FileBean("04", "2", "分类", "2"));
        list.add(new FileBean("001", "04", "hahahah", "3"));
        list.add(new FileBean("003", "04", "yayayyayya", "3"));
        list.add(new FileBean("3", "0", "事迹", "1"));
        list.add(new FileBean("07", "3", "你猜", "2"));
        list.add(new FileBean("08", "3", "你再猜", "2"));
        list.add(new FileBean("09", "3", "你猜到了吗", "2"));
        list.add(new FileBean("10", "3", "haha", "2"));
        mAdapter = new ExpandTreeListAdapter<FileBean>(this, mBinding.lvMainList, list, R.layout.item_list_activity_layout, BR.node, DEFAULT_EXPAND);
        mBinding.setAdapter(mAdapter);
    }
}
