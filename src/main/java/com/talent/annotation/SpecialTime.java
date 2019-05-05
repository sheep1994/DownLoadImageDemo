package com.talent.annotation;

import com.talent.enums.DateTimeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间注解。 标注在字段上 注明该字段使用哪种格式显示时间
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialTime {

    /**
     * 显示的日期格式
     * @return
     */
    DateTimeEnum value();
}
