package cn.miss.utils;

import cn.miss.entity.ClassEntity;

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
            //jar路径
            String runtimePath = CurrencyUtils.getJarLocation();
            if (runtimePath.endsWith(".jar")) {
                JarFile jarFile = new JarFile(runtimePath);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    String name = jarEntry.getName();//  cn/miss/entity/ZHAnswer.class
                    if (name.endsWith(".class")) {
                        if (name.startsWith(packName.substring(1))) {
                            list.add(name.replace("/", "."));
                        }
                    }
                }
            } else {
                //class路径
                File file = new File(runtimePath + packName);
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

    public static List<ClassEntity> getClassEntityByPackageName(String pack) {
        List<ClassEntity> list = new ArrayList<>();
        List<Class> classInstances = getClassInstances(pack);
        classInstances.forEach(clazz -> list.add(new ClassEntity(clazz)));
        return list;
    }

    //获取指定包内注解类
    public static <T, D extends Annotation>
    Map<D, Class<T>> getInstances(String pack, Class<D> annotation, Class<T> superClass) {
        Map<D, Class<T>> map = new HashMap<>();
        List<ClassEntity> entities = getClassEntityByPackageName(pack);
        entities.stream().filter(c -> c.getSuperClass().equals(superClass) && c.containAnnotation(annotation))
                .forEach(c -> map.put(c.getAnnotation(annotation), c.getClazz()));
        return map;
    }

    public static <T, D extends Annotation>
    Map<D, Class<T>> getInstances(String pack, Class<D> annotation) {
        Map<D, Class<T>> map = new HashMap<>();
        List<ClassEntity> entities = getClassEntityByPackageName(pack);
        entities.stream().filter(c -> c.containAnnotation(annotation))
                .forEach(c -> map.put(c.getAnnotation(annotation), c.getClazz()));
        return map;
    }
}
