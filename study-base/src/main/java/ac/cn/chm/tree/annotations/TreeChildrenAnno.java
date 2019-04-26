package ac.cn.chm.tree.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TreeChildrenAnno {
    String key() default TreeAnno.CHILDREN;
    String target();
}
