package cn.miss.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author MissNull
 * @Description: 解析类注入
 * @Date: Created in 2017/9/5.
 */
public class ParseInjection {


    public static List<String> getClasses(String packName) {
        List<String> list = new ArrayList<>();
        try {
            String runtimePath = CurrencyUtils.getJarLocation(ParseInjection.class);
            if (runtimePath != null) {
                JarFile jarFile = new JarFile(runtimePath);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    String name = jarEntry.getName();
                    if (name.startsWith(packName)) {
                        String substring = name.substring(0, name.indexOf(packName));
                        if (!substring.contains("/") && name.length() - 5 > packName.length()) {
                            list.add(name.replace("/", "."));
                        }
                    }
                }
            } else {
                runtimePath = CurrencyUtils.getRunningTimeLocation(packName);
                File file = new File(runtimePath);
                if (file.isDirectory()) {
                    File[] files = file.listFiles(pathname -> pathname.getName().endsWith(".class"));
                    Optional.ofNullable(files).ifPresent(fs -> Arrays.stream(fs).forEach(f -> {
                        String name = f.getName();
                        list.add(packName.replace("/", ".").substring(1) + "." + name);
                    }));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Class> getClassInstances(String pack) {
        List<String> packages = getClasses(pack);
        List<Class> list = new ArrayList<>();
        packages.forEach(s -> {
            try {
                list.add(Class.forName(s.substring(0, s.lastIndexOf("."))));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    //获取指定包内注解类
    public static <T, D extends Annotation> Map<D, Class<T>> getInstances(String pack, Class<D> annotation, Class<T> tClass) {
        Map<D, Class<T>> map = new HashMap<>();
        List<Class> classes = getClassInstances(pack);
        classes.stream().filter(clazz -> clazz.getSuperclass().equals(tClass) && clazz.getAnnotation(annotation) != null)
                .forEach(clazz -> {
                    D d = (D) clazz.getAnnotation(annotation);
                    map.put(d, clazz);
                });
        return map;
    }
}
