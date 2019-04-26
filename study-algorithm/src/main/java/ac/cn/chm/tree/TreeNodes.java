package ac.cn.chm.tree;

import ac.cn.chm.tree.annotations.TreeAnno;
import ac.cn.chm.util.CheckUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用列表转换为树类
 */
public class TreeNodes extends HashMap implements Comparable<TreeNodes> {//实现Comparable接口，实现根据level排序

//    private static String targetLevel = TreeAnno.LEVEL;//获取顺序的level值//多线程会炸的吧


    /**
     * 获取子树
     *
     * @param children 子树的key值
     * @return
     */
    public List<TreeNodes> getChildren(String children) {
        if (!this.containsKey(children) || CheckUtils.isEmpty(this.get(children))) {//如果不存在
            this.put(children, new ArrayList<TreeNodes>());
        }
        return (List<TreeNodes>) this.get(children);
    }

    /**
     * 获取int型数据
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        Object o = this.get(key);
        if (o instanceof Integer || o instanceof Short || o instanceof Long || o instanceof Byte) {
            return (Integer) o;
        } else {
            try {
                return Integer.valueOf((String) o);
            } catch (NullPointerException ne) {
                System.out.println("level字段为空");
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("level字段为无法转换为整型数字");
            }
        }
        return null;//不会到这来，会先抛异常
    }

    /**
     * 获取string型数据
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        Object o = this.get(key);
        if (o instanceof Integer || o instanceof Short || o instanceof Long || o instanceof Byte) {
            return String.valueOf(o);
        } else {
            return (String) o;
        }
    }


    @Override
    public int compareTo(TreeNodes tn) {
        //如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
        //1排在当前的后面//-1排在当前的前面
        int big = 1, small = -1, eq = 0;
        if (CheckUtils.isEmpty(tn)) {
            return big;
        }
        Integer thisLevel = this.getInt(this.getStr(TreeAnno.SORT_LEVEL));
        Integer tnLevel = tn.getInt(tn.getStr(TreeAnno.SORT_LEVEL));
        if (CheckUtils.isEmpty(tnLevel)) {
            return big;
        } else if (CheckUtils.isEmpty(thisLevel)) {
            return small;
        } else if (tnLevel < thisLevel) {
            return big;
        } else if (tnLevel > thisLevel) {
            return small;
        }
        return eq;
    }
}
