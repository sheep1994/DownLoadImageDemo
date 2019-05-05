package com.talent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间注解。 标注在类上面
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeFiled {

    /**
     * 标注哪些字段需要进行特殊处理. 注解默认是value，在标注的时候可以不用显示写value
     * @return
     */
    String[] values();
}
