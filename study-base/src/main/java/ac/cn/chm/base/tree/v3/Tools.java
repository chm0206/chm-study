package ac.cn.chm.base.tree.v3;

import ac.cn.chm.base.entity.Menu;
import ac.cn.chm.tools.CheckUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class Tools {

    public static void main(String[] args) {//测试用例

        List<Menu> pts = getMenuList();//模拟获取列表信息
//        List<TreeNodes> list = TreeNodes.formatTree(pts, MenuTree.source, MenuTree.target);//将列表转换为树
        List<TreeNodes> list = TreeNodes.formatTree(pts, MenuTreeEnum.sourceMap, MenuTreeEnum.targetMap);//将列表转换为树
        System.out.println(JSON.toJSONString(list));
    }

    public static List<Menu> getMenuList() {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("1", "基础管理", 1, "0", "base-icon"));
        list.add(new Menu("2", "用户管理", 2, "1", "user-icon"));
        list.add(new Menu("6", "添加菜单", 3, "3", "menu-add-icon"));
        list.add(new Menu("7", "编辑菜单", 3, "3", "menu-edit-icon"));
        list.add(new Menu("3", "菜单管理", 2, "1", "menu-icon"));
        list.add(new Menu("4", "添加用户", 3, "2", "user-add-icon"));
        list.add(new Menu("8", "添加用户并分配菜单", 4, "4", "user-menu-add-icon"));
        list.add(new Menu("5", "编辑用户", 3, "2", "user-edit-icon"));
        return list;
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