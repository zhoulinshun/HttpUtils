package cn.miss.client;

import cn.miss.entity.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpGetClient implements HttpClientInterface {

    private HttpGet httpGet;
    private HttpEntity httpEntity;

    public void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore) {
        httpGet = new HttpGet(httpEntity.getUrl());
        this.httpEntity = httpEntity;
        try {
            setHeader();
            CloseableHttpResponse execute = (CloseableHttpResponse) httpClient.execute(httpGet);
            org.apache.http.HttpEntity entity = execute.getEntity();

            List<Cookie> cookies = cookieStore.getCookies();
            if (!cookies.isEmpty()) {
                cookies.forEach(c -> System.out.println(c.getName() + " : " + c.getValue()));
            } else
                System.out.println("cookie is empty");
            System.out.println(EntityUtils.toString(entity, "utf-8"));
            EntityUtils.consume(entity);
            execute.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        Map<String, String> header = httpEntity.getHeader();
        header.forEach(httpGet::setHeader);
    }
}
