package cn.miss.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/28.
 */
public class BrowserHeader {
    public static BrowserHeader ieBrowser;
    public static BrowserHeader googleBrowser;
    public static BrowserHeader fireForkBrowser;

    static {
        ieBrowser = new BrowserHeader("IE");
        googleBrowser =new BrowserHeader("GOOGLE");
        fireForkBrowser = new BrowserHeader("FIREFORK");
    }

    private Map<String,String> headers = new HashMap<>();

    public BrowserHeader(String name, String value){

    }

    public BrowserHeader(String type) {
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Accept-Language","zh-CN,zh;q=0.8");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");
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
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
