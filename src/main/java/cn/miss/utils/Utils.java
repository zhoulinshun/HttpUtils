package cn.miss.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/28.
 */
public class Utils {
    public static Map<String, String> getCookies(String cookies) {
        Map<String, String> map = new HashMap<>();
        Stream.of(cookies.split("; ")).forEach(s -> {
            String[] split = s.split("=");
            map.put(split[0], split[1]);
        });
        return map;
    }
}
