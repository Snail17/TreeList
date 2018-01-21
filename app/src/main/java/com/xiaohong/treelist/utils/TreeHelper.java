package com.xiaohong.treelist.utils;

/**
 * Created by feng on 2017/12/20.
 */

import com.xiaohong.treelist.annoatation.TreeNodeId;
import com.xiaohong.treelist.annoatation.TreeNodeLable;
import com.xiaohong.treelist.annoatation.TreeNodeLevel;
import com.xiaohong.treelist.annoatation.TreeNodePId;
import com.xiaohong.treelist.bean.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理从后台拿到的数据
 * 1. bean -> node 使用注解和反射
 * 2. 给node设定关系，设置好父节点和子节点
 * 3. 根据关系设置节点的图标
 * 4. 过滤可展示的根节点
 * 5. 辅助工具方法：过滤出可见的节点，根据展开设置显示
 */
public class TreeHelper<T> {

    /**
     * 1. 将从服务器拿到的数据转化为Node类型
     * 使用注解和反射的原因：1. 服务器返回的数据的类型可以随意，只要注解正确
     *
     * @param mDatas 数据源
     * @param <T>    数据源的类型
     * @return 返回的数据
     */
    public static <T> List<Node> convertDates2Nodes(List<T> mDatas) {
        List<Node> nodes = new ArrayList<>();
        for (T t : mDatas) {
            String id = "";
            String pId = "";
            String lable = "";
            String level = "";
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (field.getAnnotation(TreeNodeId.class) != null) {
                        field.setAccessible(true);
                        id = (String) field.get(t);
                    }
                    if (field.getAnnotation(TreeNodePId.class) != null) {
                        field.setAccessible(true);
                        pId = (String) field.get(t);
                    }
                    if (field.getAnnotation(TreeNodeLable.class) != null) {
                        field.setAccessible(true);
                        lable = ((String) field.get(t));
                    }
                    if (field.getAnnotation(TreeNodeLevel.class) != null) {
                        field.setAccessible(true);
                        level = ((String) field.get(t));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Node node = new Node(id, pId, lable, Integer.parseInt(level));
            nodes.add(node);
        }
        // 指定 node 之间的父子关系
        for (int i = 0, len = nodes.size(); i < len; i++) {
            Node node1 = nodes.get(i);
            for (int j = i + 1; j < len; j++) {
                Node node2 = nodes.get(j);
                // node2 时 node1 的父亲
                if (node1.getPid().equals(node2.getId())) {
                    node1.setParent(node2);
                    node2.getChildren().add(node1);
                }
                // node1 时 node2 的父亲
                if (node2.getPid().equals(node1.getId())) {
                    node2.setParent(node1);
                    node1.getChildren().add(node2);
                }
            }
        }
        // 为每个节点设置图标
        for (Node node : nodes) {
            setNodeIcon(node);
        }
        return nodes;
    }


    /**
     * 如果当前节点有孩子节点并且展开状态， 设置向下的图标
     * 如果当前节点有孩子节点没有展开，设置向上的图标
     * 其他情况不设置图标
     *
     * @param node node
     */

    public static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(1);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(0);
        } else {
            node.setIcon(-1);
        }
    }

    /**
     * 从所有的节点中过滤出根节点
     *
     * @param nodes nodes
     * @return 过滤之后的数据
     */
    private static List<Node> getRootResult(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 对节点进行排序
     *
     * @param datas
     * @param defaultExpandLevel
     * @param <T>
     * @return
     */
    public static <T> List<Node> getSortNodes(List<T> datas, int defaultExpandLevel) {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertDates2Nodes(datas);
        List<Node> rootNodes = getRootResult(nodes);
        for (Node node : rootNodes) {
            // TODO:??????
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 将一个节点的所有孩子节点都放入result中
     *
     * @param result
     * @param node               当前节点
     * @param defaultExpandLevel 默认初始化是展开几层
     * @param currentLevel       当前节点层级
     */
    public static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel > currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeafNode()) {
            return;
        } else {
            for (int i = 0, len = node.getChildren().size(); i < len; i++) {
                addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
            }
        }
    }

    /**
     * 根据转化之后的列表，进行过滤可以展示的数据
     * @param mDatas
     * @return
     */
    public static List<Node> getVisibleNodes(List<Node> mDatas) {
        List<Node> result = new ArrayList<>();
        for (Node node : mDatas) {
            if (node.isRoot() || node.getParent().isExpand()) {
                result.add(node);
                if (node.getChildren() != null && node.getChildren().size() > 0) {
                    getVisibleNodes(node.getChildren());
                }
            }
        }
        return result;
    }
}
