package cn.miss.utils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author MissNull
 * @Description: 通用工具类
 * @Date: Created in 2017/8/31.
 */
public class CurrencyUtils {
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


    public static String getJarParentLocation() {
        String v;
        if ((v = getJarLocation()) != null) {
            File parentFile = new File(v).getParentFile();
            return parentFile.getAbsolutePath();
        }
        return null;
    }

    //获取当前jar运行目录
    public static String getJarLocation() {
        URL url = CurrencyUtils.class.getProtectionDomain().getCodeSource().getLocation();
        return url.getPath();
    }

    public static String getPackageLocation(String pack) {
        return CurrencyUtils.class.getResource(pack).getPath();
    }


    //正则匹配  返回单条
    public static String getReg(String reg, String src, int group) {
        List<String> reg1 = getRegs(reg, src, group);
        if (reg1.size() > 0) return reg1.get(0);
        return "";
    }

    //正则匹配 返回多条
    public static List<String> getRegs(String reg, String src, int group) {
        List<String> list = new ArrayList<>();
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(src);
        while (matcher.find()) {
            list.add(matcher.group(group));
        }
        return list;
    }

}
