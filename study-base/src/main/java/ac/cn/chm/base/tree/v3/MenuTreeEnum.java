package ac.cn.chm.base.tree.v3;

import java.util.HashMap;
import java.util.Map;

public enum MenuTreeEnum {
    //必须参数
    ID(TreeNodes.ID, "menuId", "id"),
    PID(TreeNodes.PID, "parentId", "pId"),
    LEVEL(TreeNodes.LEVEL, "menuLevel", "level"),
    CHILDREN(TreeNodes.CHILDREN, "", "children"),
    //非必须参数
    NAME("menuName", "menuName", "name"),
    ICON("menuIcon", "menuIcon", "icon"),
    URL("menuUrl", "menuUrl", "path");

    public String key;//绑定key
    public String source;//源数据key值
    public String target;//目标数据key值

    MenuTreeEnum(String key, String source, String target) {//能将指定列表里的source值转换为target，并返回TreeNodes树
        this.key = key;
        this.source = source;
        this.target = target;
    }

    public static Map<String, String> sourceMap = new HashMap<>();//源数据key
    public static Map<String, String> targetMap = new HashMap<>();//目标数据key

    static {
        MenuTreeEnum[] enums = values();
        for (MenuTreeEnum e : enums
        ) {
            sourceMap.put(e.key, e.source);
            targetMap.put(e.key, e.target);
        }

    }
}
