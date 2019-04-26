package reflex;

import java.lang.reflect.Array;

public class ArrayTest {
    public static void main(String[] args) {
        Object arr = Array.newInstance(String.class,10);
        Array.set(arr,0,"第1条数据，下标为0");
        Array.set(arr,1,"第2条数据，下标为1");
        Array.set(arr,5,"第6条数据，下标为6");
        Array.set(arr,6,"第7条数据，下标为7");
        //依次取出arr数组中index为5、6的元素的村
        Object str1 = Array.get(arr,0);
        System.out.println(str1);
        Object str2 = Array.get(arr,1);
        System.out.println(str2);
        Object str5 = Array.get(arr,5);
        System.out.println(str5);
        Object str6 = Array.get(arr,6);
        System.out.println(str6);

        Object intArr = Array.newInstance(int.class,10);
        Array.setInt(intArr,0,1);
        Array.setInt(intArr,1,2);
        Array.setInt(intArr,5,6);
        Array.setInt(intArr,6,7);
        //依次取出arr数组中index为5、6的元素的村
        int int1 = Array.getInt(intArr,0);
        System.out.println(int1);
        Object int2 = Array.getInt(intArr,1);//测试object是否可以接收基础数据类型，答案是可以的
        System.out.println(int2);
        int int5 = Array.getInt(intArr,5);
        System.out.println(int5);
        int int6 = Array.getInt(intArr,6);
        System.out.println(int6);
    }
}
