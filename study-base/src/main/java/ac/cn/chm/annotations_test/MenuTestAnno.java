package ac.cn.chm.annotations_test;


import ac.cn.chm.annotations_test.treeanno.TreeExtend;
import lombok.Data;

@Data
public class MenuTestAnno {


    private String menuId;
    @TreeExtend(target = "name")
    private String menuName;
    private String menuLevel;
    private String parentId;
    private String menuUrl;
    private String menuIcon;




}
