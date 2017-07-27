package cn.miss.client.builder;


import cn.miss.client.CloseableHttpsClientBuilder;
import cn.miss.client.HttpGetMethodClient;
import cn.miss.client.HttpMethodClient;
import cn.miss.client.HttpPostMethodClient;
import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.Map;
import java.util.Optional;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpClientBuilder {

    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpsClient;


//    public void getStart(String url, Map<String, String> headers, CookieStore cookieStore) {
//        client = new HttpGetMethodClient();
//        HttpEntity httpEntity = new HttpEntity(url, headers);
//        client.doStart(httpEntity, getHttpClient(url, cookieStore), cookieStore);
//    }
//
//    public void postStart(String url, Map<String, String> headers) {
//        client = new HttpPostMethodClient();
//    }
//
//    private void doStart(String url, Map<String, String> headers, CookieStore cookieStore) {
//        client = new HttpGetMethodClient();
//        HttpEntity httpEntity = new HttpEntity(url, headers);
//        client.doStart(httpEntity, getHttpClient(url, cookieStore), cookieStore);
//    }

    public void start(HttpEntity httpEntity, ParseString parseString) {
        HttpMethodClient client;
        CookieStore cookieStore = getCookieStore(httpEntity.getCookie());
        if (httpEntity.getMethod().equals("GET")) {
            client = new HttpGetMethodClient();
        } else {
            client = new HttpPostMethodClient();
        }
        client.doStart(httpEntity, getHttpClient(httpEntity.getUrl(), cookieStore), cookieStore,parseString);
    }

    public CookieStore getCookieStore(Map<String,String> cookie){
        CookieStore cookieStore = new BasicCookieStore();
        cookie.forEach((s,e)->{
            BasicClientCookie c = new BasicClientCookie(s,e);
            cookieStore.addCookie(c);
        });
        return cookieStore;
    }

    private CloseableHttpClient getHttpClient(String url, CookieStore cookieStore) {
        if (url.startsWith("http:"))
            return Optional.ofNullable(httpClient).orElseGet(() -> (httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build()));
        else
            return Optional.ofNullable(httpsClient).orElseGet(() -> (httpsClient = CloseableHttpsClientBuilder.getCloseableHttpClient(cookieStore)));
    }

}
