package com.talent.logic.convert;

import com.talent.annotation.SpecialTime;
import com.talent.annotation.TimeFiled;
import com.talent.enums.DateTimeEnum;
import com.talent.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: DTO转VO工具类
 * @create: 2019-04-18 17:12
 */
public class DtoVoConvert {

    private static final Logger logger = LoggerFactory.getLogger(DtoVoConvert.class);


    /**
     * list集合对象拷贝
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<E> convertList(List<T> sourceList, Class<?> targetClass) {
        logger.info("list集合对象拷贝操作开始...");
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<E> targetList = new ArrayList<E>();
        for (T t : sourceList) {
            E e = convert(t, targetClass);
            targetList.add(e);
        }
        return targetList;
    }

    /**
     * 对象拷贝
     * @param source 源对象
     * @param targetClass 目标类
     * @param <E>
     * @return
     */
    public static <E> E convert(Object source, Class<?> targetClass) {
        logger.info("对象拷贝操作开始...");
        if (source == null) {
            return null;
        }
        E target = null;
        try {
            // 实例化对象
            target = (E) targetClass.newInstance();
            // 拷贝简单类型
            BeanUtils.copyProperties(source, target);
            /**
             * 对时间进行特殊处理
             * asSubclass : 判断一个类是不是另外一个类的子类，返回Class
             */
            copySpecialTime(source, source.getClass().asSubclass(source.getClass()), target, targetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 对时间进行特殊处理
     * @param source
     * @param sourceClass
     * @param target
     * @param targetClass
     * @param <E>
     */
    private static <E> void copySpecialTime(Object source, Class<?> sourceClass, E target, Class<?> targetClass) {
        List<String> values = new ArrayList<>();
        if (Objects.isNull(source)) {
            return ;
        }
        // 判断源类是否是标注了@TimeField注解
        if (sourceClass.isAnnotationPresent(TimeFiled.class)) {
            values.addAll(Arrays.asList(sourceClass.getAnnotation(TimeFiled.class).values()));
        }
        // 判断目标类上是否标注了@TimeField注解
        if (targetClass.isAnnotationPresent(TimeFiled.class)) {
            values.addAll(Arrays.asList(targetClass.getAnnotation(TimeFiled.class).values()));
        }
        // 根据对应的值找到对应的字段信息
        if (!CollectionUtils.isEmpty(values)) {
            for (String value : values) {
                try {
                    // 获取源类中对应的字段
                    Field sourceField = sourceClass.getDeclaredField(value);
                    Field targetField = targetClass.getDeclaredField(value);
                    deepCopy(sourceField, targetField, source, target);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static <E> void deepCopy(Field sourceField, Field targetField, Object source, E target) {
        // 获取类中对应字段的类型
        Class<?> sourceType = sourceField.getType();
        Class<?> targetType = targetField.getType();
        try {
            // 源类中为String类型的时间，目标类中为Date类型的时间
            if (sourceType.equals(String.class) && targetType.equals(Date.class)) {
                DateTimeEnum sourceTimeEnum = sourceField.getAnnotation(SpecialTime.class).value();
                if (Objects.isNull(sourceTimeEnum)) {
                    return ;
                }
                // 获取源类中的getter方法
                Method sourceMethod = source.getClass().getMethod("get" + sourceField.getName().substring(0, 1).toUpperCase() + sourceField.getName().substring(1, sourceField.getName().length()));
                // 获取到对应的值
                String invoke = (String) sourceMethod.invoke(source);
                // 获取目标类中的setter方法
                Method targetMethod = target.getClass().getMethod("set" + targetField.getName().substring(0, 1).toUpperCase() + targetField.getName().substring(1, targetField.getName().length()), targetType);
                // 设置值
                targetMethod.invoke(target, DateUtil.convertStrToDate(invoke, sourceTimeEnum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
