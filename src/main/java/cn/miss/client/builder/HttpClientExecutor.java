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
public class HttpClientExecutor {

    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpsClient;

    public void start(HttpEntity httpEntity, ParseString parseString) {
        HttpMethodClient client;
        CookieStore cookieStore = getCookieStore(httpEntity.getCookie());
        if (httpEntity.getMethod().equals("GET")) {
            client = new HttpGetMethodClient();
        } else {
            //暂时都是get处理
            //client = new HttpPostMethodClient();
            client = new HttpGetMethodClient();
        }
        client.doStart(httpEntity, getHttpClient(httpEntity.getUrl(), cookieStore), cookieStore, parseString);
    }

    private CookieStore getCookieStore(Map<String, String> cookie) {
        CookieStore cookieStore = new BasicCookieStore();
        Optional.ofNullable(cookie).ifPresent(d->d.forEach((s, e) -> {
            BasicClientCookie c = new BasicClientCookie(s, e);
            c.setDomain("www.zhihu.com");
            c.setPath("/");
            cookieStore.addCookie(c);
        }));
        return cookieStore;
    }

    private CloseableHttpClient getHttpClient(String url, CookieStore cookieStore) {
        if (url.startsWith("http:"))
            return Optional.ofNullable(httpClient).orElseGet(() -> (httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build()));
        else
            return Optional.ofNullable(httpsClient).orElseGet(() -> (httpsClient = CloseableHttpsClientBuilder.getCloseableHttpClient(cookieStore)));
    }

}
