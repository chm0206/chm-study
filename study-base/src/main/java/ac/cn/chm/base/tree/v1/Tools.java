package ac.cn.chm.base.tree.v1;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class Tools {

    public static void main(String[] args) {//测试用例
        TreeNodes keys = new TreeNodes("ptId", "parentId", "ptName", "ptLevels");
        //String str = "[{\"parentId\":\"0\",\"ptDeleted\":\"0\",\"ptId\":\"1\",\"ptLevels\":1,\"ptName\":\"类型1\"},{\"parentId\":\"0\",\"ptDeleted\":\"0\",\"ptId\":\"2\",\"ptLevels\":1,\"ptName\":\"类型2\"},{\"parentId\":\"2\",\"ptDeleted\":\"0\",\"ptId\":\"3\",\"ptLevels\":2,\"ptName\":\"类型23\"}]";
        //String s1 = changeKey(str, keys);
        //System.out.println(s1);
        List<Map<String, Object>> pts = getListMap();//模拟获取列表信息
        List<TreeNodes> list = Tools.formatTree(pts, keys);//将列表转换为树
        System.out.println(JSON.toJSONString(list));
    }

    public static List<Map<String, Object>> getListMap() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("ptId", 6); map6.put("parentId", 3); map6.put("ptLevels", 2); map6.put("ptName", "测试用例6");
        list.add(map6);
        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("ptId", 7);  map7.put("parentId", 4); map7.put("ptLevels", 3); map7.put("ptName", "测试用例7");
        list.add(map7);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("ptId", 1); map1.put("parentId", 0); map1.put("ptLevels", 1); map1.put("ptName", "测试用例1");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("ptId", 2); map2.put("parentId", 0); map2.put("ptLevels", 1); map2.put("ptName", "测试用例2");
        list.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("ptId", 3); map3.put("parentId", 0); map3.put("ptLevels", 1); map3.put("ptName", "测试用例3");
        list.add(map3);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("ptId", 4); map4.put("parentId", 1); map4.put("ptLevels", 2); map4.put("ptName", "测试用例4");
        list.add(map4);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("ptId", 5); map5.put("parentId", 2); map5.put("ptLevels", 2); map5.put("ptName", "测试用例5");
        list.add(map5);

        Map<String, Object> map8 = new HashMap<String, Object>();
        map8.put("ptId", 8);  map8.put("parentId", 5); map8.put("ptLevels", 3); map8.put("ptName", "测试用例8");
        list.add(map8);

        return list;
    }

    /**
     * @param list 树的内容，需要按照level从小到大进行排序，否则无法获取到第一个层级，且易造成树的分支丢失
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> List<TreeNodes> formatTree(List<T> list, TreeNodes keys) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }
        String jsonStr = JSON.toJSONString(list);
        jsonStr = changeKey(jsonStr, keys);//change，不改变格式，不需要这段代码，直接删除
        List<TreeNodes> treeNodes = listClone(jsonStr, TreeNodes.class);//change，不改变格式，不需要这段代码，直接删除
        
        Collections.sort(treeNodes);//排序
        
        Map<String, List<TreeNodes>> map = new HashMap<>();//change，修改List为指定的类型
        //转换字段
        List<TreeNodes> root = null;//根目录 //change，修改List为指定的类型

        for (TreeNodes current : treeNodes) {//change，修改List为指定的类型
            {//添加当前元素到指定级别
                String level = current.getLevel();//change，修改获取层级的方法
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
                List<TreeNodes> parentTree = map.get(String.valueOf(Integer.valueOf(current.getLevel()) - 1));//change，修改List、获取层级的方法
                if (parentTree == null) {
                    continue;
                }
                for (TreeNodes parent : parentTree) {//change，修改List为指定的类型
                    if (parent.getId().equals(current.getPId())) {//如果找不到父级，则为异常数据，抛弃   //change，修改上下级关联的判断依据
                        parent.getChildren().add(current);
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
    public static String changeKey(String json, TreeNodes keys) {

        if (StringUtils.isEmpty(json) || keys == null) {
            return "";
        }
        json = json.replaceAll(keys.getId(), "id")
                .replaceAll(keys.getText(), "text")
                .replaceAll(keys.getPId(), "pId")
                .replaceAll(keys.getLevel(), "level");
        return json;
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