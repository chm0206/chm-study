
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ObjectPoolFactory {
    private Map<String, Object> objectPool = new HashMap<>();
    private Properties config = new Properties();
    {
        config.setProperty("java.util.Properties%property","property");
    }

    private Object createObject(String clazzName) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class<?> clazz = Class.forName(clazzName);
        Object t = clazz.newInstance();
        return t;
    }

    public void initPool(String... classNames) throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        for (String name : classNames
        ) {
            objectPool.put(name, createObject(name));
        }
    }

    public void initProperty() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (String name : config.stringPropertyNames()
        ) {
            if (name.contains("%")) {
                String[] objAndProp = name.split("%");
                Object target = getObject(objAndProp[0]);
                //获取setter方法名:set+"首字母大写"+剩下部分
                String mtdName = "set" + objAndProp[1].substring(0, 1).toUpperCase() + objAndProp[1].substring(1);
                Class<?> targetClass = target.getClass();
                //获取想要调用的方法
                Method mtd = targetClass.getMethod(mtdName, String.class,String.class);
                //获取Method的invoke方法执行setter方法
                mtd.invoke(target, config.getProperty(name));//第二个为传入method的参数
            }
        }
    }

    public Object getObject(String name) {
        return this.objectPool.get(name);
    }

    public static void main(String[] args) throws Exception {
        ObjectPoolFactory pf = new ObjectPoolFactory();
        pf.initPool("java.util.HashMap", "java.util.Properties");
        System.out.println(pf.getObject("java.util.HashMap").getClass());
        System.out.println(pf.getObject("java.util.Properties").getClass());

        pf.initProperty();
    }
}
