package ac.cn.chm.tree.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TreeAnno {
    String ID = "id";//主键
    String LEVEL = "level";//层级
    String PID = "pId";//父级主键
    String CHILDREN = "children";//子树

    String SORT_LEVEL = "sorl_level";

    /**
     * 保存到map里的key值
     *
     * @return
     */
    String key();

    /**
     * 保存到map里的target值
     *
     * @return
     */
    String target();
}
