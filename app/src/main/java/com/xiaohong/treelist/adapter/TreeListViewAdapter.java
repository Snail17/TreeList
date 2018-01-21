package com.xiaohong.treelist.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaohong.treelist.bean.Node;
import com.xiaohong.treelist.utils.TreeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2017/12/21.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    protected List<Node> mAllNodes = new ArrayList<>();
    protected Context mContext;
    protected ListView listView;
    protected LayoutInflater inflater;
    protected List<Node> mVisibleNodes;
    protected int layoutId;
    protected int variabledId;

    protected OnTreeNodeClickListener onTreeNodeClickListener;

    public TreeListViewAdapter(Context context, ListView listView, List<T> datas, int layoutId, int variabledId, int defaultExpandLevel) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.listView = listView;
        this.layoutId = layoutId;
        this.variabledId = variabledId;
        mAllNodes = TreeHelper.getSortNodes(datas, defaultExpandLevel);
        mVisibleNodes = TreeHelper.getVisibleNodes(mAllNodes);
        listView.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return mVisibleNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node = mVisibleNodes.get(i);
        view = getConvertView(node, i, view, viewGroup);
        view.setPadding(40 * node.getLevel(), 4, 4, 4);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        expandOrCollapse(i);
        if (onTreeNodeClickListener != null) {
            onTreeNodeClickListener.setOnClick(mVisibleNodes.get(i), i, view);
        }
    }

    /**
     * 设置点击展开或者收缩
     * @param position position
     */
    private void expandOrCollapse(int position) {
        Node node = mVisibleNodes.get(position);
        if (node != null) {
            if (node.isLeafNode()) {
                return;
            }
            node.setExpand(!node.isExpand());
            mVisibleNodes = TreeHelper.getVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public interface OnTreeNodeClickListener {
        void setOnClick(Node node, int position, View view);
    }
    public abstract View getConvertView(Node node, int position, View view, ViewGroup viewGroup);

}
