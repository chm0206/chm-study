package ac.cn.chm.tree;

import ac.cn.chm.tools.CheckUtils;
import ac.cn.chm.tree.annotations.TreeAnno;
import ac.cn.chm.tree.annotations.TreeChildrenAnno;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TreeAnalysis {
    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static Map<String, Map<String, Map<String, String>>> treeMap = new HashMap<>();
//    {//map
//        Menu:{//map
//            source:{//map
//
//            },target:{
//
//            }
//        },...
//    }

    public static <T> Map<String, Map<String, String>> analysis(Class<T> t) {
        if (CheckUtils.isNotEmpty(t) && treeMap.containsKey(t.toString())) {
            return treeMap.get(t.toString());
        } else {
            return getSourceAndTarget(t);
        }

    }

    public static <T> Map<String, String> source(Class<T> t) {
        if (CheckUtils.isNotEmpty(t) && treeMap.containsKey(t.toString())) {
            return treeMap.get(t.toString()).get(SOURCE);
        } else {
            Map<String, Map<String, String>> tree = getSourceAndTarget(t);
            return CheckUtils.isNotEmpty(tree) ? tree.get(SOURCE) : null;
        }
    }

    public static <T> Map<String, String> target(Class<T> t) {
        if (CheckUtils.isNotEmpty(t) && treeMap.containsKey(t.toString())) {
            return treeMap.get(t.toString()).get(TARGET);
        } else {
            Map<String, Map<String, String>> tree = getSourceAndTarget(t);
            return CheckUtils.isNotEmpty(tree) ? tree.get(TARGET) : null;
        }
    }

    public static <T> String getLevel(Class<T> t) {
        if (CheckUtils.isNotEmpty(t) && !treeMap.containsKey(t.toString())) {
            Map<String, Map<String, String>> tree = getSourceAndTarget(t);
            if (CheckUtils.isEmpty(tree)) {
                return null;
            }
//            else{//在下面执行了
//                tree.get(TARGET).get(TreeAnno.LEVEL);
//            }
        }
        return treeMap.get(t.toString()).get(TARGET).get(TreeAnno.LEVEL);
    }

    private static <T> Map<String, Map<String, String>> getSourceAndTarget(Class<T> t) {
        Map<String, String> source = new HashMap<>();
        Map<String, String> target = new HashMap<>();
        Field[] fields = null;
        fields = t.getDeclaredFields();//获取所有的字段
        try {
            for (Field f : fields
            ) {
                Annotation[] annotations = t.getDeclaredField(f.getName()).getAnnotations();
                for (Annotation a : annotations
                ) {
                    if (a instanceof TreeAnno) {
                        //注解是对的，添加到指定源及目标中
                        TreeAnno ta = (TreeAnno) a;
                        source.put(ta.key(), f.getName());
                        target.put(ta.key(), ta.target());
                    }
                }
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //校验是否有id,pid,level这三个属性
        if (checkField(source) && checkField(target)) {//还是说自动补齐呢？
            target.put(TreeAnno.CHILDREN, getChildren(t));

            Map<String, Map<String, String>> tree = new HashMap<>();
            tree.put(SOURCE, source);
            tree.put(TARGET, target);
            treeMap.put(t.toString(), tree);
            return tree;
        } else {
            //抛异常
        }
//        annotations = t.
        return null;
    }

    public static <T> String getChildren(Class<T> t) {
        Annotation a = t.getAnnotation(TreeChildrenAnno.class);

        //这里还没有成功
        String children = a == null ? TreeAnno.CHILDREN : ((TreeChildrenAnno) a).target();
        return children.isEmpty() || children == "" ? TreeAnno.CHILDREN : children;
    }

    /**
     * 校验是否拥有id,pId,level这三个属性
     *
     * @param map
     * @return
     */
    private static boolean checkField(Map<String, String> map) {
        boolean existId = false, existPId = false, existLevel = false;// existChildren = false;
        for (String key : map.keySet()) {
            switch (key) {
                case TreeAnno.ID:
                    existId = true;
                    break;
                case TreeAnno.PID:
                    existPId = true;
                    break;
                case TreeAnno.LEVEL:
                    existLevel = true;
                    break;
//                case TreeAnno.CHILDREN:
//                    existChildren = true;
//                    break;
                default:
            }
        }
        return existId && existLevel && existPId;//&& existChildren;
    }

}
