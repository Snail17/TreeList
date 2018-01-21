package com.xiaohong.treelist.bean;



import com.xiaohong.treelist.annoatation.TreeNodeId;
import com.xiaohong.treelist.annoatation.TreeNodeLable;
import com.xiaohong.treelist.annoatation.TreeNodeLevel;
import com.xiaohong.treelist.annoatation.TreeNodePId;


/**
 * Created by feng on 2017/12/21.
 */

public class FileBean  {
    @TreeNodeId
    public String code;
    @TreeNodePId
    public String parentCode;
    @TreeNodeLable
    public String name;
    @TreeNodeLevel
    public String level;


    public FileBean(String code, String parentCode, String name, String level) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
