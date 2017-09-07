package cn.miss.ano;

import java.lang.annotation.*;
import java.text.Annotation;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/9/5.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ParseImpl {
    String value();
}
