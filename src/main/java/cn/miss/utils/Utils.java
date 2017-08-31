package cn.miss.utils;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
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
            StringBuilder value = new StringBuilder(split[1]);
            if (split.length > 2) {
                value.append(split[1]);
                value.append("=");
                for (int i = 2; i < split.length; i++) {
                    value.append(split[i]);
                    if (i != split.length - 1) {
                        value.append("=");
                    }
                }
            }
            map.put(split[0], value.toString());
        });
        return map;
    }


    //文件保存
    public static String fileSave(byte[] bytes, String filename, String path) {
        File p = new File(path);
        if (!p.exists())
            p.mkdirs();
        File f = new File(p, filename);
        try {
            if (!f.exists())
                f.createNewFile();
            Files.write(f.toPath(), bytes);
            return filename + ": 保存成功";
        } catch (Exception e) {
            e.printStackTrace();
            return filename + ": 保存失败" + e.getMessage();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("jdbc:mysql:com.mysql.jdbc.Driver");
            return DriverManager.getConnection("http://59.110.239.11:3306", "root", "1049");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
