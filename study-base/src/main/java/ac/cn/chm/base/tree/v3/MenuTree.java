package ac.cn.chm.base.tree.v3;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MenuTree {

    public static final Map<String, String> source = new HashMap<String, String>();
    public static final Map<String, String> target = new HashMap<String, String>();

    static {//必须属性
        source.put(TreeNodes.ID, "menuId");
        target.put(TreeNodes.ID, "id1");

        source.put(TreeNodes.PID, "parentId");
        target.put(TreeNodes.PID, "pId1");

        source.put(TreeNodes.LEVEL, "menuLevel");
        target.put(TreeNodes.LEVEL, "level1");


        target.put(TreeNodes.CHILDREN, "children111");
    }

    static {//非必须属性
        source.put("menuName", "menuName");
        target.put("menuName", "name");
        source.put("menuIcon", "menuIcon");
        target.put("menuIcon", "icon");
        source.put("menuPath", "menuUrl");
        target.put("menuPath", "path");
    }
}
