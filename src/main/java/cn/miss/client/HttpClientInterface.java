package cn.miss.client;

import cn.miss.entity.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public interface HttpClientInterface {
    void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore);
}
