package ac.cn.chm.annotations_test;

import java.lang.annotation.Annotation;

public class TestMain {
    public static void main(String[] args) {
        MenuTestAnno anno = new MenuTestAnno();
        anno.setMenuName("abc");
    }

//    //在xx的时候会执行解析
//    public static void analysis(MenuTestAnno anno) {
//        Annotation[] annotations = null;
//        try {
//            annotations = Class.forName("MenuTestAnno").getAnnotations();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//    }
}
