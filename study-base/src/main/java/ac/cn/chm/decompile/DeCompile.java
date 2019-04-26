package ac.cn.chm.decompile;

import java.io.PrintStream;

public class DeCompile {
    public static final String a = "test";
    public static final String b = "btest";
    public static final String c = getC() + "test";
    public static final String d = getD() + "test";

    public static void main(String[] paramArrayOfString) {
        System.out.println("test");
    }

    public static String getC() {
        return "c";
    }

    public final static String getD() {
        return "D";
    }
}