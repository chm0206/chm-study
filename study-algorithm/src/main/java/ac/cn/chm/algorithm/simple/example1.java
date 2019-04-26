package ac.cn.chm.algorithm.simple;

public class example1 {

    public static void main(String[] args) {
        //遍历00001到11111
        binarySystem(16,2);

    }

    /**
     * 进制转换并输出
     * @param binary 几进制
     * @param digit 数字的位数
     */
    public static void binarySystem(int binary,int digit){
        int count = 1;
        for (int i = 0; i < digit; i++) {count = count *binary;}
        for (int i = 0; i < count; i++) {
//            String s = Integer.toBinaryString(i);
            String s = Integer.toString(i,binary);
            System.out.println(getStr(digit-s.length())+s);
        }
    }
    public static String getStr(int a){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a; i++) {
            sb.append("0");
        }
        return sb.toString();
    }
}
