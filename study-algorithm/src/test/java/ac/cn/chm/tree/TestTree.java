package java.ac.cn.chm.tree;

import ac.cn.chm.tree.TreeNodes;
import ac.cn.chm.util.FormatUtils;
import com.alibaba.fastjson.JSON;

import java.ac.cn.chm.tree.MenuTestAnno;
import java.util.ArrayList;
import java.util.List;

public class TestTree {
    public static void main(String[] args) {
        List<MenuTestAnno> menu = new ArrayList<>();
        menu.add(new MenuTestAnno("1","菜单1","1","0","http://baidu.com/1","icon-1"));
        menu.add(new MenuTestAnno("2","菜单2","1","0","http://baidu.com/2","icon-2"));
        menu.add(new MenuTestAnno("3","菜单3","3","4","http://baidu.com/3","icon-3"));
        menu.add(new MenuTestAnno("4","菜单4","2","1","http://baidu.com/4","icon-4"));
        menu.add(new MenuTestAnno("5","菜单5","2","2","http://baidu.com/5","icon-5"));
        menu.add(new MenuTestAnno("6","菜单6","3","5","http://baidu.com/6","icon-6"));
        menu.add(new MenuTestAnno("7","菜单7","3","4","http://baidu.com/7","icon-7"));
        menu.add(new MenuTestAnno("8","菜单8","4","7","http://baidu.com/8","icon-8"));
        menu.add(new MenuTestAnno("9","菜单9","4","6","http://baidu.com/9","icon-9"));

        List<TreeNodes> nodes = FormatUtils.list2Tree(menu,MenuTestAnno.class);
        System.out.println(JSON.toJSONString(nodes));
        List<TreeNodes> nodes1 = FormatUtils.list2TreeByAnno(menu,MenuTestAnno.class);
        System.out.println(JSON.toJSONString(nodes1));
    }
}
