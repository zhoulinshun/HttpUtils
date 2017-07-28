package cn.miss.parse;

import cn.miss.client.HttpMethodClient;
import org.apache.http.HttpRequest;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/27.
 */
public interface ParseString {
    List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore);
    void pretreatment(String url, HttpRequestBase client);
}
