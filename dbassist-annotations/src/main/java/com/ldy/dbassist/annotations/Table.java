package com.ldy.dbassist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ldy on 2017/5/11.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * @return name
     */
    String value() default "";

    /**
     * @return 表名是否为常量
     */
    boolean isConstantName() default true;
    boolean allFields() default true;

    /**
     * @return class.
     */
    Class inherit() default Object.class;
}
