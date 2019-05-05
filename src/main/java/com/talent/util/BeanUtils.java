package com.talent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: Bean 对象拷贝工具类
 * @create: 2019-04-30 15:37
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {}

    /**
     * Bean 对象拷贝
     * @param sourceObj 源对象
     * @param target 目标类
     * @param <T> 目标对象
     * @return 返回目标对象
     */
    public static <T> T copyProperties(Object sourceObj, T target) {
        logger.info("bean 对象拷贝操作开始... sourceObj : [{}], target : [{}]", sourceObj, target);
        if (Objects.isNull(sourceObj) || Objects.isNull(target)) {
            return target;
        }
        // sourceObj 是不是 Map 这种对象
        if (sourceObj instanceof Map) {
            // 转换为Map
            Map map = (Map)sourceObj;
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry next = (Map.Entry) iterator.next();
                for (Field field : target.getClass().getDeclaredFields()) {
                    if (field.getName().equals(next.getKey())) {
                        Object value = next.getValue();
                        if (Objects.isNull(value)) {
                            break;
                        }
                        // 判断Object能不能转换成这种类型
                        if (field.getType().isInstance(value)) {
                            field.setAccessible(true);
                            try {
                                field.set(target, value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else /* 是 JavaBean 对象 */ {
            for (Field field : sourceObj.getClass().getDeclaredFields()) {
                Object value = null;
                field.setAccessible(true);
                try {
                    value = field.get(sourceObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (Objects.isNull(value)) {
                    continue;
                }
                for (Field targetField : target.getClass().getDeclaredFields()) {
                    // 字段必须匹配一致
                    if (field.getName().equals(targetField.getName())) {
                        if (targetField.getType().isInstance(value)) {
                            targetField.setAccessible(true);
                            try {
                                targetField.set(target, value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if /* 该类型是否为原始类型 */ (targetField.getType().isPrimitive() && targetField.getType().hashCode() == value.getClass().hashCode()) {
                            targetField.setAccessible(true);
                            try {
                                targetField.set(target, value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
                
            }
        }
        return target;
    }

    public static <T> T copyProperties(Object sourceObj, Class<?> clazz) {
        if (Objects.isNull(sourceObj)) {
            return null;
        }
        T target = null;
        try {
            target = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return copyProperties(sourceObj, target);
    }

    public static <T> List<T> copyListProperties(List<Object> sourceObjs, Class<?> clazz) {
        if (CollectionUtils.isEmpty(sourceObjs)) {
            return null;
        }
        List<T> targetObjs = new ArrayList<>();
        for (Object sourceObj : sourceObjs) {
            targetObjs.add(copyProperties(sourceObj, clazz));
        }
        return targetObjs;
    }
}
