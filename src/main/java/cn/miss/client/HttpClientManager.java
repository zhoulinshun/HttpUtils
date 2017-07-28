package cn.miss.client;

import cn.miss.client.builder.HttpClientExecutor;
import cn.miss.entity.HttpEntity;
import cn.miss.utils.BrowserHeader;
import cn.miss.utils.CallBack;
import cn.miss.utils.Utils;

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
    private HttpClientExecutor executor;
    private CallBack callback;

    public HttpClientManager(){
        executor = new HttpClientExecutor();
    }

    public HttpClientManager setCallback(CallBack callback){
        this.callback = callback;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
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

    public void start(){
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setCookie(Utils.getCookies(cookies));
        httpEntity.setUrl(url);
        httpEntity.setMethod(method);
        switch (browser){
            case "IE":
                httpEntity.setHeader(BrowserHeader.ieBrowser.getHeaders());
                break;
            case "":
//                httpEntity.setHeader(BrowserHeader.googleBrowser);
                break;
        }
    }
}
