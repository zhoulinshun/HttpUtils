package cn.miss.entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * @Author MissNull
 * @Description: 管理Class内接口和注解
 * @Date: Created in 2017/9/18.
 */
public class ClassEntity {
    private List<Class> interfaces;
    private List<? extends Annotation> annotations;
    private Class superClass;

    private Class clazz;

    public ClassEntity(Class clazz) {
        annotations = Arrays.asList(clazz.getAnnotations());
        interfaces = Arrays.asList(clazz.getInterfaces());
        superClass = clazz.getSuperclass();
        this.clazz = clazz;
    }

    public Class getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public List<Class> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Class> interfaces) {
        this.interfaces = interfaces;
    }

    public List<? extends Annotation> getAnnotations() {
        return annotations;
    }

    public boolean containAnnotation(Class<? extends Annotation> clazz) {
        for (int i = 0; i < annotations.size(); i++) {
            if (clazz.equals(annotations.get(i).annotationType())) return true;
        }
        return false;
    }

    public <T extends Annotation> T getAnnotation(Class<T> clazz) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(clazz)) {
                return (T) annotation;
            }
        }
        return null;
    }

    public void setAnnotations(List<? extends Annotation> annotations) {
        this.annotations = annotations;
    }


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
