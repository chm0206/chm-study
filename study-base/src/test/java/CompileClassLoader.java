import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class CompileClassLoader extends ClassLoader {
    private byte[] getBytes(String filename)throws IOException{
        File file = new File(filename);
        long len = file.length();
        byte[] raw = new byte[(int)len];
        try(
        FileInputStream fis = new FileInputStream(file))
        {
            //一次读取Class文件的全部二进制数据
            int r = fis.read(raw);
            if(r != len)
                throw new IOException("无法读取全部文件:"+r+"!="+len);
            return raw;
        }
    }

    private boolean compile(String javaFile) throws IOException{
        System.out.println("CompileClassLoader:正在编译"+javaFile+"...");
        Process p = Runtime.getRuntime().exec("javac "+javaFile);//javac后面有一个空格
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int ret = p.exitValue();
            return ret == 0;
    }
    //重写ClassLoader的findClass方法
    protected  Class<?> findClass(String name) throws ClassNotFoundException{
        Class clazz = null;
        //将包路径中的点(.)替换成斜线(/)
        String fileStub = name.replace(".","/");
        String javaFilename = fileStub+".java";
        String classFilename = fileStub+".class";
        File javaFile = new File(javaFilename);
        File classFile  = new File(classFilename);
        //当指定Java源文件存在，且Class文件不存在，或者java源文件的修改时间比class文件的修改时间更晚时，重新编译
        if (javaFile.exists() && (!classFile.exists())
        ||javaFile.lastModified()>classFile.lastModified()){
            try {
                if(!compile(javaFilename) || !classFile.exists()){
                    throw new ClassNotFoundException("ClassNotFoundException:"+javaFilename);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(classFile.exists()){
            try {
                byte[] raw = getBytes(classFilename);
                clazz = defineClass(name,raw,0,raw.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(clazz == null){
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    public static void main(String[] args) throws Exception {
        //如果运行该程序时没有参数(即没有目标类时)
        if(args.length<1){
            System.out.println("缺少目标类、请按如下格式运行java源文件：");
            System.out.println("java CompileClassLoader ClassName");
        }
        //第一个参数是需要运行的类
        String progClass = args[0];
        //剩下的参数将作为运行目标类时的参数
        //将这些参数复制到一个新数组中
        String[] proArgs = new String[args.length-1];
        System.arraycopy(args,1,proArgs,0,proArgs.length);
        CompileClassLoader ccl  = new CompileClassLoader();
        //加载需要运行的类
        Class<?> clazz = ccl.loadClass(progClass);
        //获取需要运行的类的主方法
        Method main = clazz.getMethod("main",(new String[0]).getClass());
        Object argsArray[] = {proArgs};
        main.invoke(null,argsArray);
    }
}
