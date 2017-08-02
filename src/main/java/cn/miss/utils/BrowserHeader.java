package cn.miss.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/28.
 */

public class BrowserHeader {
    public static BrowserHeader ieBrowser= new BrowserHeader("IE");
    public static BrowserHeader googleBrowser=new BrowserHeader("GOOGLE");
    public static BrowserHeader fireForkBrowser= new BrowserHeader("FIREFORK");
    public static BrowserHeader androidBrowser = new BrowserHeader("ANDROID");
    public static BrowserHeader iphoneBrowser = new BrowserHeader("IPHONE");

    private Map<String,String> headers = new HashMap<>();

    public BrowserHeader(String name, String value){

    }

    public BrowserHeader(String type) {
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Accept-Language","zh-CN,zh;q=0.8");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");
        headers.put("Cache-Control","max-age=0");
//        headers.put("Host","www.zhihu.com");
        switch (type) {
            case "IE":
                headers.put("User-Agent","");
                break;
            case "GOOGLE":
                headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
                break;
            case "FIREFORK":
                headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
                break;
            case "ANDROID":
                headers.put("User-Agent","Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
            case "IPHONE":
                headers.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/59.0.3071.115 Mobile/13B143 Safari/601.1.46");
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
