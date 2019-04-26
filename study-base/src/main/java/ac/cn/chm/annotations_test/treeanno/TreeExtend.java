package ac.cn.chm.annotations_test.treeanno;



import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface TreeExtend {
    String target();
}
