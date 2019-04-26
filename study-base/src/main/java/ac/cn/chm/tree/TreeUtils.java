package ac.cn.chm.tree;

import ac.cn.chm.tools.CheckUtils;
import ac.cn.chm.tools.FormatUtils;
import ac.cn.chm.tree.annotations.TreeAnno;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class TreeUtils {
    /**
     * 将实体类型转换为指定key值的map
     *
     * @param t      树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行数据字段更新的数据类型
     * @return
     */
    public static <T> TreeNodes formatBean(T t, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(t, source, target)) {
            return new TreeNodes();
        }
        String json = JSON.toJSONString(t);
        try {
            return getNodesBytarger(JSON.parseObject(json), source, target);
        } catch (Exception e) {
            return new TreeNodes();
        }
    }

    /**
     * 按map对列表进行数据拷贝操作
     *
     * @param list   要进行拷贝的列表数据集合
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行数据字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> formatList(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(list, source, target)) {
            return new ArrayList<>();
        }
        try {
            return getCurrencyTreeNodes(list, source, target);//保存多余的字段
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 将指定实体转换为map
     *
     * @param list   树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行数据字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> formatListByMap(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(list, source, target)) {
            return new ArrayList<>();
        }
        try {
            return getCurrencyTreeNodesByMap(list, source, target);//不保存多余的字段
//            return getCurrencyTreeNodes(list, source, target);//保存多余的字段
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 将列表转换为树
     *
     * @param list   树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行数据字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> formatTree(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }

        List<TreeNodes> treeNodes = formatList(list, source, target);

        Collections.sort(treeNodes);//排序//排序是肯定要的啊

        return list2Tree(treeNodes, source, target);
    }

    /**
     * 按map里的字段将列表转换为树
     *
     * @param list   树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行数据字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> formatTreeByMap(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }

        List<TreeNodes> treeNodes = formatListByMap(list, source, target);

        Collections.sort(treeNodes);//排序//排序是肯定要的啊

        return list2Tree(treeNodes, source, target);
    }

    /**
     * 将已处理好格式的列表转换为树
     *
     * @param treeNodes 已转换为treeNodes的数据集合
     * @param source    未更新前的字段集合
     * @param target    更新后的字段集合
     * @return
     */
    public static List<TreeNodes> list2Tree(List<TreeNodes> treeNodes, Map<String, String> source, Map<String, String> target) {
        Map<String, List<TreeNodes>> map = new HashMap<>();//change，修改List为指定的类型
        //转换字段
        List<TreeNodes> root = null;//根目录 //change，修改List为指定的类型

        for (TreeNodes current : treeNodes) {//change，修改List为指定的类型
            {//添加当前元素到指定级别
                String level = current.getStr(target.get(TreeAnno.LEVEL));//change，修改获取层级的方法
                if (!map.containsKey(level)) {//不存在，先添加list
                    map.put(level, new ArrayList<TreeNodes>());//change，修改List为指定的类型
                }
                List<TreeNodes> arr = map.get(level);//当前层级//change，修改List为指定的类型
                arr.add(current);
                current.remove(TreeAnno.SORT_LEVEL);//清除辅助用的字段
                if (root == null) {//表示是第一级
                    root = arr;
                }
            }

            //将当前元素添加到父级的子元素列表里
            {
                List<TreeNodes> parentTree = map.get(String.valueOf(current.getInt(target.get(TreeAnno.LEVEL)) - 1));//change，修改List、获取层级的方法
                if (parentTree == null) {
                    continue;
                }
                for (TreeNodes parent : parentTree) {//change，修改List为指定的类型
                    if (parent.get(target.get(TreeAnno.ID)).equals(current.get(target.get(TreeAnno.PID)))) {//如果找不到父级，则为异常数据，抛弃   //change，修改上下级关联的判断依据
                        parent.getChildren(target.get(TreeAnno.CHILDREN)).add(current);
                        break;
                    }
                }
            }
        }

        return root;
    }

    /**
     * 更新字段
     *
     * @param json   要进行字段更新的字符串
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @return
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
     * 获取CurrencyTreeNodes，不去除多余字段
     *
     * @param list   要进行字段更新的数据集合
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> getCurrencyTreeNodes(List<T> list, Map<String, String> source, Map<String, String> target) {

        String jsonStr = JSON.toJSONString(list);
        jsonStr = changeKey(jsonStr, source, target);//change，不改变格式，不需要这段代码，直接删除
        List<TreeNodes> treeNodes = FormatUtils.listClone(jsonStr, TreeNodes.class);//change，不改变格式，不需要这段代码，直接删除
        for (TreeNodes t : treeNodes) {
            t.put(TreeAnno.SORT_LEVEL, target.get(TreeAnno.LEVEL));//保存到常量里方便获取
        }
        return treeNodes;
    }

    /**
     * 根据keys里的字段获取CurrencyTreeNodes
     *
     * @param list   要进行字段更新的数据集合
     * @param source 未更新前的字段集合
     * @param target 更新后的字段集合
     * @param <T>    要进行字段更新的数据类型
     * @return
     */
    public static <T> List<TreeNodes> getCurrencyTreeNodesByMap(List<T> list, Map<String, String> source, Map<String, String> target) {
        if (CheckUtils.isOneEmpty(list, source, target)) {
            return null;
        }
        String json = JSON.toJSONString(list);
        List<JSONObject> objects = FormatUtils.listClone(list, JSONObject.class);
        List<TreeNodes> nodes = new ArrayList<>();
        for (JSONObject t : objects) {
            nodes.add(getNodesBytarger(t, source, target));
        }
        return nodes;
    }

    /**
     * 将JSONObject转换为treeNodes对象
     *
     * @param object json对象
     * @param source json对象的格式
     * @param target treeNodes对象的格式
     * @return
     */
    public static TreeNodes getNodesBytarger(JSONObject object, Map<String, String> source, Map<String, String> target) {
        TreeNodes treeNodes = new TreeNodes();
        for (String key : source.keySet()) {
            treeNodes.put(target.get(key), object.get(source.get(key)));
        }
        treeNodes.put(TreeAnno.SORT_LEVEL, target.get(TreeAnno.LEVEL));//保存到常量里方便获取
        return treeNodes;
    }
}
