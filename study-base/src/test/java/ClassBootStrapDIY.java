import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ClassBootStrapDIY {
    public static void main(String[] args)
            throws IOException {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器：" + systemLoader);
        Enumeration<URL> eml = systemLoader.getResources("");
        while (eml.hasMoreElements()) {
            System.out.println(eml.nextElement());
        }
        ClassLoader extensionLader = systemLoader.getParent();
        System.out.println("扩展类加载器：" + extensionLader);
        System.out.println("扩展类加载器路径：" + System.getProperty("java.ext.dirs"));
        System.out.println("扩展类加载器parent：" + extensionLader.getParent());
    }
}
//step1=>1.检测Class是否载入过
//        step2=>2.父类加载是否存在
//        step3=>3.请求使用父类加载器去载入目标
//        step4=>4.请求使用根类加载器去载入目标
//        step5=>5.当前类加载器尝试寻找claa文件
//        step6=>6.从文件中载入class
//        step7=>7.抛出classNotFoundException异常
//        step8=>8.返回对应的java.lang.Class对象
//        st->op