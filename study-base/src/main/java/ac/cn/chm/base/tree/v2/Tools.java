package ac.cn.chm.base.tree.v2;

import ac.cn.chm.base.entity.Menu;
import ac.cn.chm.tools.CheckUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class Tools {

    public static void main(String[] args) {//测试用例

        Map<String,String> changeKey = new HashMap<>();
        {//必须字段
            changeKey.put("menuId", TreeNodes.ID);//将“menuId”转换为TreeNodes.ID
            changeKey.put("menuLevel", TreeNodes.LEVEL);
            changeKey.put("parentId", TreeNodes.PID);
        }
        {//非必须字段
            changeKey.put("menuName", TreeNodes.NAME);
            changeKey.put("menuIcon", TreeNodes.ICON);
        }
        //可自定义更多字段

        List<Menu> pts = getMenuList();//模拟获取列表信息
        List<TreeNodes> list = Tools.formatTree(pts, changeKey);//将列表转换为树
        System.out.println(JSON.toJSONString(list));
    }

    public static List<Menu> getMenuList(){
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("1","基础管理",1,"0","base-icon"));
        list.add(new Menu("2","用户管理",2,"1","user-icon"));
        list.add(new Menu("6","添加菜单",3,"3","menu-add-icon"));
        list.add(new Menu("7","编辑菜单",3,"3","menu-edit-icon"));
        list.add(new Menu("3","菜单管理",2,"1","menu-icon"));
        list.add(new Menu("4","添加用户",3,"2","user-add-icon"));
        list.add(new Menu("8","添加用户并分配菜单",4,"4","user-menu-add-icon"));
        list.add(new Menu("5","编辑用户",3,"2","user-edit-icon"));
        return list;
    }


    /**
     * @param list 树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> List<TreeNodes> formatTree(List<T> list, Map<String,String> keys) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }

        //List<TreeNodes> treeNodes = getTreeNodes(list,keys);//保存多余的字段
        List<TreeNodes> treeNodes = getTreeNodesByMap(list,keys);//不保存多余的字段

        Collections.sort(treeNodes);//排序

        Map<String, List<TreeNodes>> map = new HashMap<>();//change，修改List为指定的类型
        //转换字段
        List<TreeNodes> root = null;//根目录 //change，修改List为指定的类型

        for (TreeNodes current : treeNodes) {//change，修改List为指定的类型
            {//添加当前元素到指定级别
                String level = current.getStr(TreeNodes.LEVEL);//change，修改获取层级的方法
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
                List<TreeNodes> parentTree = map.get(String.valueOf(current.getInt(TreeNodes.LEVEL) - 1));//change，修改List、获取层级的方法
                if (parentTree == null) {
                    continue;
                }
                for (TreeNodes parent : parentTree) {//change，修改List为指定的类型
                    if (parent.get(TreeNodes.ID).equals(current.get(TreeNodes.PID))) {//如果找不到父级，则为异常数据，抛弃   //change，修改上下级关联的判断依据
                        ((List<TreeNodes>)parent.get(TreeNodes.CHILDREN)).add(current);
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
    public static String changeKey(String json, Map<String,String> keys) {


        if (CheckUtils.isOneEmpty(json,keys)) {
            return "";
        }
        for (Map.Entry<String,String> entry : keys.entrySet()) {
            json = json.replaceAll(entry.getKey(),entry.getValue());
        }
        return json;
    }

    /**
     * 获取TreeNodes，不去除多余字段
     */
    public static <T>  List<TreeNodes> getTreeNodes(List<T> list, Map<String,String> keys) {

        String jsonStr = JSON.toJSONString(list);
        jsonStr = changeKey(jsonStr, keys);//change，不改变格式，不需要这段代码，直接删除
        List<TreeNodes> treeNodes = listClone(jsonStr, TreeNodes.class);//change，不改变格式，不需要这段代码，直接删除
        return treeNodes;
    }
    /**
     * 根据keys里的字段获取TreeNodes
     */
    public static <T> List<TreeNodes> getTreeNodesByMap(List<T> list,Map<String,String> keys){
        if (CheckUtils.isOneEmpty(list,keys)) {
            return null;
        }
        String json = JSON.toJSONString(list);
        List<JSONObject> objects = listClone(list,JSONObject.class);
        List<TreeNodes> nodes = new ArrayList<>();
        for (JSONObject t: objects  ) {
            TreeNodes treeNodes = new TreeNodes();
            for (Map.Entry<String,String> entry : keys.entrySet()) {
                treeNodes.put(entry.getValue(),t.get(entry.getKey()));
            }
            nodes.add(treeNodes);
        }
        return nodes;
    }

    /**
     * 将一个对象的列表转换为另一个对象的列表
     *
     * @param k
     * @param clazz
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<T> listClone(List<K> k, Class<T> clazz) {
        List<T> list = JSONArray.parseArray((JSONArray.toJSON(k)).toString(), clazz);
        return list;
    }

    /**
     * 将json格式的字符串转换为指定对象的列表
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> listClone(String str, Class<T> clazz) {
        List<T> list = JSONArray.parseArray(str, clazz);
        return list;
    }
}