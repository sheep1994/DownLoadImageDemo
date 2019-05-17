package com.talent.util;

import com.talent.comparator.ClassNameComparator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: 扫描Jar包中的class文件工具类
 * @create: 2019-05-16 17:02
 */
public class JarFileUtil {

    /**
     * 扫描jar包中的class文件
     * @param basePackage jar路径
     * @return 返回扫描到的class文件集合
     */
    public static Set<Class<?>> doScanClassesByJar(String basePackage) throws IOException, ClassNotFoundException {
        Set<Class<?>> classSet = new TreeSet<>(new ClassNameComparator());
        if (StringUtils.endsWith(basePackage, ".")) {
            basePackage = StringUtils.substring(basePackage, 0, StringUtils.lastIndexOf(basePackage, "."));
        }
        basePackage = StringUtils.replace(basePackage, ".", "/");
        // 获取当前项目路径下的jar, 建议使用Thread.currentThread().getContextClassLoader()来获取资源文件
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(basePackage);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if (StringUtils.equals("jar", protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (!StringUtils.startsWith(name, basePackage) || entry.isDirectory()) {
                        continue;
                    }
                    String className = StringUtils.substring(name, 0, StringUtils.lastIndexOf(name, ".")).replace("/", ".");
                    classSet.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                }
            }
        }
        return classSet;
    }

    public static void main(String[] args) {
        try {
            Set<Class<?>> classSet = doScanClassesByJar("org.junit");
            for (Class<?> targetClz : classSet) {
                System.out.println(targetClz);
            }
        } catch (Exception e) {}

    }
}
