package com.talent.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: list集合工具
 * @create: 2019-05-05 16:59
 */
public class ListUtil {

    private static final String DEFAULT_SEPARATOR = ",";

    public static <T> List<T> convertStrToList(String str, Class<?> clazz) {
        return convertStrToList(str, DEFAULT_SEPARATOR, clazz);
    }

    /**
     * 将字符串转为集合
     * @param str
     * @param separator
     * @param <T>
     * @return
     */
    public static <T> List<T> convertStrToList(String str, String separator, Class<?> clazz) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strs = str.split(separator);
        List list = new ArrayList<>();
        for (String s : strs) {
            if (clazz.equals(String.class)) {
                list.add(s);
            } else if (clazz.equals(Integer.class)) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    /**
     * list转换成字符串
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String join(List<T> list) {
        return join(list, DEFAULT_SEPARATOR);
    }

    /**
     * list转换成字符串
     * @param list
     * @param separator
     * @param <T>
     * @return
     */
    public static <T> String join(List<T> list, String separator) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        // 循环集合
        for (int i = 0, len = list.size(); i < len; i++) {
            buffer.append(list.get(i));
            if (i < len - 1) {
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        List<String> list = ListUtil.convertStrToList("1,2,3", String.class);
        System.out.println(list);
        List<Integer> integerList = ListUtil.convertStrToList("1,2,3", Integer.class);
        System.out.println(integerList);

        System.out.println(ListUtil.join(list, ","));
    }
}
