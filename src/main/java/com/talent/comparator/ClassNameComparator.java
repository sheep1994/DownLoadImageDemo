package com.talent.comparator;

import java.util.Comparator;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 类名称比较器
 * @create: 2019-05-16 17:07
 */
public class ClassNameComparator implements Comparator<Class<?>> {
    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        // 全类名比较
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
