package com.xiaohong.treelist.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiaohong.treelist.bean.Node;
import com.xiaohong.treelist.databinding.ItemListActivityLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2017/12/21.
 */

public class ExpandTreeListAdapter<T> extends TreeListViewAdapter {

    public ExpandTreeListAdapter(Context context, ListView listView, List<T> datas,int layoutId, int variabledId, int defaultExpandLevel) {
        super(context, listView, datas, layoutId,variabledId, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, final int position, View convertView, ViewGroup viewGroup) {
        ItemListActivityLayoutBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(variabledId, mVisibleNodes.get(position));
        if (node.isExpand()) {
            binding.tvItemListText.setTextColor(Color.WHITE);
            binding.getRoot().setBackgroundColor(Color.RED);
        } else {
            binding.tvItemListText.setTextColor(Color.BLACK);
            binding.getRoot().setBackgroundColor(Color.WHITE);
        }
        return binding.getRoot();
    }

    /**
     * 返回所的node
     *
     * @return
     */
    public List<Node> getSelectedNodes() {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0, len = mAllNodes.size(); i < len; i++) {
            Node node = ((Node) mAllNodes.get(i));
            if (node.isChoose()) {
                nodeList.add(node);
            }
        }
        return nodeList;
    }

}
