package tree;

import ac.cn.chm.tree.annotations.TreeAnno;
import ac.cn.chm.tree.annotations.TreeChildrenAnno;

//@Data
@TreeChildrenAnno(target = "children")//,sort = "level"
public class MenuTestAnno {


    @TreeAnno(key = TreeAnno.ID, target = "id")
    private String menuId;
    @TreeAnno(key = "name", target = "name")
    private String menuName;
    @TreeAnno(key = TreeAnno.LEVEL, target = "level1")
    private String menuLevel;
    @TreeAnno(key = TreeAnno.PID, target = "pId")
    private String parentId;
    @TreeAnno(key = "url", target = "path")
    private String menuUrl;
//    @TreeAnno(key = "icon", target = "icon")
    private String menuIcon;

    public MenuTestAnno(String menuId, String menuName, String menuLevel, String parentId, String menuUrl, String menuIcon) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuLevel = menuLevel;
        this.parentId = parentId;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
