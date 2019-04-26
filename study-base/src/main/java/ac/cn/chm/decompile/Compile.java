package ac.cn.chm.decompile;

public class Compile {
    public static final String a = "test";
    public static final String b = "b"+a;

    public static final String c = getC()+a;

    public static final String d = getD() + a;

    static{
        String test = a;
    }

    public static void main(String[] args) {
        System.out.println(a);
    }
    public static String  getC(){
        return "c";
    }
    public final static String getD() {
        return "D";//可以提交吗？
    }
}

