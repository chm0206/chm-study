package ac.cn.chm.base.entity;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@Data
public class Menu {
    private String menuId;
    private String menuName;
    private Integer menuLevel;
    private String parentId;
    private String menuIcon;
    private String menuUrl;
    private String testOther;

    //alt+insert-->Constructor：创建构造器
    public Menu(String menuId, String menuName, Integer menuLevel, String parentId, String menuIcon) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuLevel = menuLevel;
        this.parentId = parentId;
        this.menuIcon = menuIcon;
        this.menuUrl = menuIcon+menuName;
        this.testOther = menuId +menuIcon;
    }
    public Menu(){

    }
    private void setMenuId(String id){
        this.menuId = id;
    }

    public static void main(String[] args) throws NoSuchMethodException,IllegalAccessException, InvocationTargetException,InstantiationException {
        Class c = Menu.class;
        Constructor con = c.getConstructor();
        Menu menu = (Menu) con.newInstance();
        Method m = c.getDeclaredMethod("setMenuId", String.class);
        m.setAccessible(false);
        m.invoke(menu,"id1111");
        System.out.println(1);
//        System.out.println(menu.getMenuId());
    }
}
