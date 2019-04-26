package reflex;

import java.lang.reflect.Array;

public class ArrayTest2 {
    public static void main(String[] args) {
        //创建一个三维数组
        Object arr = Array.newInstance(String.class,3,4,5);
        //获取到index为2的元素，该元素是一个二维数组
        Object arrObj = Array.get(arr,2);
        Array.set(arrObj,2,new String[]{"第一条数据","第二条数据"});
        //获取到一维的数组
        Object arrObj2 = Array.get(arrObj,3);
        Array.set(arrObj2,4,"第5条数据");
//        Array.set(arrObj2,5,"第6条数据");//数据下标最大值比长度小1，因为是从0开始，获取所有的元素，这里会报java.lang.ArrayIndexOutOfBoundsException
        String[][][] cast = (String[][][])arr;//向下转型，需要强制转换
        System.out.println(cast[2][3][4]);
        System.out.println(cast[2][2][1]);
        System.out.println(cast[2][2][0]);
        System.out.println(cast[0][0][0]);//此空间没有赋值，为默认值null
    }
}
