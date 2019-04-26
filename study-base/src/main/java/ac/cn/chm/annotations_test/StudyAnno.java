package ac.cn.chm.annotations_test;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Override方法的覆写
//@Deprecated//标记该方法、类、键值已过时
//@SuppressWarnings
//@Retention(RetentionPolicy.CLASS)//该注解保存到编译阶段
@Retention(RetentionPolicy.RUNTIME)//该注解保存到运行阶段
//@Retention(RetentionPolicy.SOURCE)//该注解保存到源码阶段，编译直接丢弃这种注解
//@Target()//定义该注解可以修饰哪些程序单元，注解、构造器、成员变量、局部变量、方法、包、参数、类接口枚举
@Documented//该注解会添加到api文档
@Inherited//添加该注解之后，该注解具有继承性
public @interface StudyAnno {
}
