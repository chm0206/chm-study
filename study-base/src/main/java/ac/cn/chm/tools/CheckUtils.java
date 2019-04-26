package ac.cn.chm.tools;

import java.util.Collection;
import java.util.Map;

public class CheckUtils {

    public static void main(String[] args) {
        double[] a = new double[0];
        System.out.println(isEmpty(a));

//        System.out.println("list="+isEmpty(new ArrayList<String>()));
//        System.out.println("list="+isEmpty(new HashSet<String>()));
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else {
            if (o instanceof String && o.toString().trim().equals("")) {        //字符串判断
                return true;
            } else if (o instanceof Iterable && ((Collection) o).size() <= 0) { //判断set和list
                return true;
            } else if (o instanceof Map && ((Map) o).size() == 0) {             //map判断
                return true;
            } else if (o instanceof Object[] && ((Object[]) ((Object[]) o)).length == 0) {//object判断
                return true;
            } else if (isEmptyBasicDataArray(o)) {              //基础数据类型数组判断
                return true;
            }
            return false;
        }
    }
    /**
     * 判断传入所有可变参数是否有一个为空
     * @param o
     * @return
     */
    public static boolean isOneEmpty(Object ... o){
        for (Object obj :o ) {
            if(isEmpty(obj)){//有一个为空，符合条件
                return true;
            }
        }
        return false;
    }

    /**
     * 判断传入所有可变参数是否都为空
     * @param o
     * @return
     */
    public static boolean isAllEmpty(Object ... o){
        for (Object obj :o ) {
            if(!isEmpty(obj)){//有一个不为空，则不符合条件
                return false;
            }
        }
        return true;
    }

    /**
     * 判断传入可变参数是否有一个参数不为空
     * @param o
     * @return
     */
    public static boolean isOneNotEmpty(Object ... o){
        for (Object obj :o ) {
            if(!isEmpty(obj)){//有一个不为空，符合条件
                return true;
            }
        }
        return false;
    }

    /**
     * 判断传入所有可变参数是否都非空
     * @param o
     * @return
     */
    public static boolean isAllNotEmpty(Object ... o){
        for (Object obj :o ) {
            if(isEmpty(obj)){//有一个为空，则不符合条件
                return false;
            }
        }
        return true;
    }
    /**
     * 判断基础数据类型是否非空
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o ){
        return !isEmpty(o);
    }

    /**
     * 校验基础数据类型数组是否为空
     *
     * @param o
     * @return
     */
    public static boolean isEmptyBasicDataArray(Object o) {
        if (o == null) {
            return true;
        } else {
            if (o instanceof int[] && ((int[]) ((int[]) o)).length == 0) {
                return true;
            } else if (o instanceof long[] && ((long[]) ((long[]) o)).length == 0) {
                return true;
            } else if (o instanceof byte[] && ((byte[]) ((byte[]) o)).length == 0) {
                return true;
            } else if (o instanceof char[] && ((char[]) ((char[]) o)).length == 0) {
                return true;
            } else if (o instanceof double[] && ((double[]) ((double[]) o)).length == 0) {
                return true;
            } else if (o instanceof float[] && ((float[]) ((float[]) o)).length == 0) {
                return true;
            } else if (o instanceof short[] && ((short[]) ((short[]) o)).length == 0) {
                return true;
            } else if (o instanceof boolean[] && ((boolean[]) ((boolean[]) o)).length == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断基础数据类型数组是否非空
     * @param o
     * @return
     */
    public static boolean isNotEmptyBasicDataArray(Object o){
        return !isEmptyBasicDataArray(o);
    }

    /**
     * 判断传入可变数组是否有一个为空
     * @param o
     * @return
     */
    public static boolean isOneEmptyBasicDataArray(Object ... o){
        for (Object obj :o ) {
            if(isEmptyBasicDataArray(obj)){//有一个符合条件即可
                return true;
            }
        }
        return false;
    }
    /**
     * 判断传入可变数组是否有一个非空
     * @param o
     * @return
     */
    public static boolean isOneNotEmptyBasicDataArray(Object ... o){
        for (Object obj :o ) {
            if(!isEmptyBasicDataArray(obj)){//有一个符合条件即可
                return true;
            }
        }
        return false;
    }

    /**
     * 判断传入可变数组是否全部为空
     * @param o
     * @return
     */
    public static boolean isAllEmptyBasicDataArray(Object ... o){
        for (Object obj :o ) {
            if(!isEmptyBasicDataArray(obj)){//有一个不为空，则不符合条件
                return false;
            }
        }
        return true;
    }
    /**
     * 判断传入可变数组是否全部为空
     * @param o
     * @return
     */
    public static boolean isAllNotEmptyBasicDataArray(Object ... o){
        for (Object obj :o ) {
            if(isEmptyBasicDataArray(obj)){//有一个不为空，则不符合条件
                return false;
            }
        }
        return true;
    }
}
