import java.lang.reflect.Constructor;

public class CreateJframe {
    public static void main(String[] args) throws Exception {
        //获取Class对象
        Class<?> jframeClazz = Class.forName("javax.swing.JFrame");
        //获取JFrame中带一个字符串参数的构造器
        Constructor ctor = jframeClazz.getConstructor(String.class);
        //调用Constructor的newInstance方法创建对象
        Object obj = ctor.newInstance("测试窗口");//要传入的参数
        System.out.println(obj);
    }
}
