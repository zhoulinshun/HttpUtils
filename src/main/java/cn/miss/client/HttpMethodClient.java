package cn.miss.client;

import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public interface HttpMethodClient {
    void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore, ParseString parseString);
//    void doStart(String url, Map<String,String> headers, HttpClient httpClient, CookieStore cookieStore);
}
