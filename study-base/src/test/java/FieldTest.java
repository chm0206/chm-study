import java.lang.reflect.Field;

public class FieldTest {
    public static void main(String[] args) throws Exception{
        Person p = new Person();
        Class<Person> personClass = Person.class;
        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true);//但是好像不用也可以，可能默认是true了，等下试试
        nameField.set(p,"Yeeku.H.Lee");
        Field ageField = personClass.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.setInt(p,30);
        System.out.println(p);
//        String[][][][] a = new String[1][2][3][4];
    }
}
class Person{
    private String name ;
    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
