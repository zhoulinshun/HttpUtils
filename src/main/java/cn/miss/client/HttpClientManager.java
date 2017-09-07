package cn.miss.client;


import cn.miss.client.builder.HttpClientExecutor;
import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import cn.miss.parse.ZHParse;
import cn.miss.utils.BrowserHeader;
import cn.miss.utils.CallBack;
import cn.miss.utils.ProjectFlag;
import cn.miss.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/28.
 */
public class HttpClientManager {
    private String method;
    private String browser;
    private String urlType;
    private String url;
    private String cookies;
    private String outPath;
    private long speed;
    private Map<String, String> header = new HashMap<>();
    private HttpEntity httpEntity;
    private HttpClientExecutor executor;
    private CallBack successCallable;
    private Thread thread;
    private Map<String, Class<ParseString>> map = new HashMap<>();

    public HttpClientManager() {
        executor = new HttpClientExecutor();
    }

    public HttpClientManager setSuccessCallable(CallBack successCallable) {
        this.successCallable = successCallable;
        return this;
    }

    public Map<String, Class<ParseString>> getMap() {
        return map;
    }

    public HttpClientManager setMap(Map<String, Class<ParseString>> map) {
        this.map = map;
        return this;
    }

    public HttpClientManager setOutPath(String outPath) {
        this.outPath = outPath;
        return this;
    }

    public HttpClientManager setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpClientManager setCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    public HttpClientManager setMethod(String method) {
        this.method = method;
        return this;
    }

    public HttpClientManager setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    public HttpClientManager setUrlType(String urlType) {
        this.urlType = urlType;
        return this;
    }

    public HttpClientManager setSpeed(long speed) {
        this.speed = speed;
        return this;
    }

    public HttpClientManager setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public void start() {
        //开启全局开关
        ProjectFlag.flag = true;
        httpEntity = new HttpEntity();
        if (cookies != null && !cookies.isEmpty()) {
            httpEntity.setCookie(Utils.getCookies(cookies));
        }
        httpEntity.setUrl(url);
        httpEntity.setMethod(method);
        httpEntity.setSpeed(speed);
        httpEntity.setOutPath(outPath);
        Map<String, String> tempHeader = new HashMap<>();
        tempHeader.putAll(header);
        switch (browser) {
            case "IE":
                tempHeader.putAll(BrowserHeader.ieBrowser.getHeaders());
                break;
            case "GOOGLE":
                tempHeader.putAll(BrowserHeader.googleBrowser.getHeaders());
                break;
            case "FIREFORK":
                tempHeader.putAll(BrowserHeader.fireForkBrowser.getHeaders());
                break;
        }
        httpEntity.setHeader(tempHeader);
        ParseString parseString = getParse(urlType);
        parseString.setCallBack(successCallable);
        thread = new Thread(() -> {
            executor.start(httpEntity, parseString);
            successCallable.callback();
        });
        thread.start();
    }

    public boolean getRunnableStatus() {
        return !(thread == null || thread.isInterrupted());
    }

    public void stopRunnable() {
        ProjectFlag.flag = false;
    }

    public ParseString getParse(String value) {
        try {
            return map.get(value).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ZHParse();
    }
}
