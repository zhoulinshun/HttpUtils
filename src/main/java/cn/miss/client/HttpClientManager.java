package cn.miss.client;

import cn.miss.client.builder.HttpClientExecutor;
import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import cn.miss.utils.BrowserHeader;
import cn.miss.utils.CallBack;
import cn.miss.utils.ProjectFlag;
import cn.miss.utils.Utils;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
    private long speed;
    private Map<String, String> header = new HashMap<>();
    private HttpEntity httpEntity = new HttpEntity();
    private HttpClientExecutor executor;
    private CallBack callback;
    private Thread thread;

    public HttpClientManager() {
        executor = new HttpClientExecutor();
    }

    public HttpClientManager setCallback(CallBack callback) {
        this.callback = callback;
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
        if (cookies != null && !cookies.isEmpty()) {
            httpEntity.setCookie(Utils.getCookies(cookies));
        }
        httpEntity.setUrl(url);
        httpEntity.setMethod(method);
        httpEntity.setSpeed(speed);
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
        ParseString parseString = Utils.getParse(urlType);
        parseString.setCallBack(callback);
        thread = new Thread(() -> {
            executor.start(httpEntity, parseString);
            callback.callback();
        });
        thread.start();
    }

    public boolean getRunnableStatus() {
        return !(thread == null || thread.isInterrupted());
    }

    public void stopRunnable() {
        ProjectFlag.flag = false;
    }
}
