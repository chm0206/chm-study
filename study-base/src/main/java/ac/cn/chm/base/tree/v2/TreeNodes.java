package ac.cn.chm.base.tree.v2;

import ac.cn.chm.tools.CheckUtils;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class TreeNodes extends HashMap implements Comparable<TreeNodes> {//实现Comparable接口，实现根据level排序

    public final static String LEVEL= "level";          //节点的层级         必须字段
    public final static String CHILDREN ="children";    //子树            必须字段
    public final static String ID = "id";               //节点的ID         必须字段
    public final static String PID = "pId";             //节点的父ID      必须字段

    public final static String ICON = "icon";           //节点图标          非必须
    public final static String NAME = "name";           //节点名称          非必须

    public TreeNodes(){
        this.put(CHILDREN,new ArrayList<TreeNodes>());
        JSONObject object = new JSONObject();
    }

    public Integer getInt(String key){
        Object o = this.get(key);
        if (o instanceof Integer ||o instanceof Short ||o instanceof Long ||o instanceof Byte) {
            return (Integer)o;
        }else{
            try{
                return Integer.valueOf((String)o);
            }catch (Exception e){
                System.out.println("level字段无法转换为整型数字");
            }
        }
        return 1;//不会到这来，会先抛异常
    }

    public String getStr(String key){
        Object o = this.get(key);
        if (o instanceof Integer ||o instanceof Short ||o instanceof Long ||o instanceof Byte) {
            return String.valueOf(o);
        }else{
            return (String)o;
        }
    }
    @Override
    public int compareTo(TreeNodes tn) {
        //如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
        //1排在当前的后面//-1排在当前的前面
        int big = 1, small = -1, eq = 0;
        if(CheckUtils.isEmpty(tn)){
            return big;
        }
        Integer thisLevel = this.getInt(LEVEL);
        Integer tnLevel = tn.getInt(LEVEL);
        if (CheckUtils.isEmpty(tnLevel)) {
            return big;
        } else if (CheckUtils.isEmpty(thisLevel)) {
            return small;
        } else if (tnLevel < thisLevel) {
            return big;
        } else if (tnLevel > thisLevel) {
            return small;
        }
        return eq;
    }
}
