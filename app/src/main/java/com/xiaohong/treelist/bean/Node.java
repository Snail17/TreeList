package com.xiaohong.treelist.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2017/12/20.
 */

public class Node {
    // 自身id
    private String id;
    // 父辈id
    private String pid;
    // 显示内容
    private String name;
    // 条目的icon
    private int icon;
    // 树的层级
    private int level;
    //    是否展开
    private boolean isExpand;
    //    是否选中
    private boolean isChoose;
    // 在搜索查询的时候使用：判断本节点是否显示
    private boolean isShow;
    //    父节点
    private Node parent;
    //    儿子节点集合
    private List<Node> children = new ArrayList<>();

    public Node(String id, String pid, String content, int level) {
        this.id = id;
        this.pid = pid;
        this.name = content;
        this.level = level;
    }

    public Node() {
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String content) {
        this.name = content;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 递归实现树的层级获取
     *
     * @return level level
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 设置节点展开
     * @param expand expand
     */
    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!expand) {
            for (Node n : children) {
                n.setExpand(false);
            }
        }
    }

    /**
     * 是否时根节点
     * @return root
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 父节点是否展开
     * @return isParentExpand
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand();
    }

    /**
     * 判断是否叶子节点
     *
     * @return  left
     */
    public boolean isLeafNode() {
        return children.size() == 0;
    }


}
