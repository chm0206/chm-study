package ac.cn.chm.base.tree.v3;

import ac.cn.chm.tools.CheckUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class TreeNodes extends HashMap implements Comparable<TreeNodes> {//实现Comparable接口，实现根据level排序

    public final static String LEVEL = "level";          //节点的层级         必须字段
    public final static String CHILDREN = "children";    //子树            必须字段
    public final static String ID = "id";               //节点的ID         必须字段
    public final static String PID = "pId";             //节点的父ID      必须字段

    public static String targetLevel = LEVEL;//可以愉

    public List<TreeNodes> getChildren(String children) {
        if (!this.containsKey(children)||CheckUtils.isEmpty(this.get(children))) {//如果不存在
            this.put(children, new ArrayList<TreeNodes>());
        }
        return (List<TreeNodes>) this.get(children);
    }

    public Integer getInt(String key) {
        Object o = this.get(key);
        if (o instanceof Integer || o instanceof Short || o instanceof Long || o instanceof Byte) {
            return (Integer) o;
        } else {
            try {
                return Integer.valueOf((String) o);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("level字段无法转换为整型数字");
            }
        }
        return 1;//不会到这来，会先抛异常
    }

    public String getStr(String key) {
        Object o = this.get(key);
        if (o instanceof Integer || o instanceof Short || o instanceof Long || o instanceof Byte) {
            return String.valueOf(o);
        } else {
            return (String) o;
        }
    }

    @Override
    public int compareTo(TreeNodes tn) {
        //如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
        //1排在当前的后面//-1排在当前的前面
        int big = 1, small = -1, eq = 0;
        if (CheckUtils.isEmpty(tn)) {
            return big;
        }
        Integer thisLevel = this.getInt(targetLevel);
        Integer tnLevel = tn.getInt(targetLevel);
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


    /**
     * @param list   树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<TreeNodes> formatTree(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }
        //更新targetLevel，用于遍历比较
        targetLevel = target.get(LEVEL);

//        List<TreeNodes> treeNodes = getTreeNodes(list,source, target);//保存多余的字段
        List<TreeNodes> treeNodes = getTreeNodesByMap(list, source, target);//不保存多余的字段

        Collections.sort(treeNodes);//排序

        Map<String, List<TreeNodes>> map = new HashMap<>();//change，修改List为指定的类型
        //转换字段
        List<TreeNodes> root = null;//根目录 //change，修改List为指定的类型

        for (TreeNodes current : treeNodes) {//change，修改List为指定的类型
            {//添加当前元素到指定级别
                String level = current.getStr(target.get(TreeNodes.LEVEL));//change，修改获取层级的方法
                if (!map.containsKey(level)) {//不存在，先添加list
                    map.put(level, new ArrayList<TreeNodes>());//change，修改List为指定的类型
                }
                List<TreeNodes> arr = map.get(level);//当前层级//change，修改List为指定的类型
                arr.add(current);
                if (root == null) {//表示是第一级
                    root = arr;
                }
            }

            //将当前元素添加到父级的子元素列表里
            {
                List<TreeNodes> parentTree = map.get(String.valueOf(current.getInt(target.get(TreeNodes.LEVEL)) - 1));//change，修改List、获取层级的方法
                if (parentTree == null) {
                    continue;
                }
                for (TreeNodes parent : parentTree) {//change，修改List为指定的类型
                    if (parent.get(target.get(TreeNodes.ID)).equals(current.get(target.get(TreeNodes.PID)))) {//如果找不到父级，则为异常数据，抛弃   //change，修改上下级关联的判断依据
                        parent.getChildren(target.get(TreeNodes.CHILDREN)).add(current);
                        break;
                    }
                }
            }
        }

        return root;
    }

    /**
     * 更新字段
     */
    public static String changeKey(String json, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(json, source, target)) {
            return "";
        }
        for (String key : source.keySet()) {
            json = json.replaceAll(source.get(key), target.get(key));
        }
        return json;
    }

    /**
     * 获取TreeNodes，不去除多余字段
     */
    public static <T> List<TreeNodes> getTreeNodes(List<T> list, Map<String, String> source, Map<String, String> target) {

        String jsonStr = JSON.toJSONString(list);
        jsonStr = changeKey(jsonStr, source, target);//change，不改变格式，不需要这段代码，直接删除
        List<TreeNodes> treeNodes = Tools.listClone(jsonStr, TreeNodes.class);//change，不改变格式，不需要这段代码，直接删除
        return treeNodes;
    }

    /**
     * 根据keys里的字段获取TreeNodes
     */
    public static <T> List<TreeNodes> getTreeNodesByMap(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(list, source, target)) {
            return null;
        }
        String json = JSON.toJSONString(list);
        List<JSONObject> objects = Tools.listClone(list, JSONObject.class);
        List<TreeNodes> nodes = new ArrayList<>();
        for (JSONObject t : objects) {
            TreeNodes treeNodes = new TreeNodes();
            for (String key : source.keySet()) {
                treeNodes.put(target.get(key), t.get(source.get(key)));
            }
            nodes.add(treeNodes);
        }
        return nodes;
    }
}
