package cn.miss.client;

import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpGetMethodClient implements HttpMethodClient {


    private HttpGet httpGet;
    private HttpEntity httpEntity;
    private CookieStore cookieStore;


    public void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore, ParseString parseString) {
        httpGet = new HttpGet();
        this.httpEntity = httpEntity;
        this.cookieStore = cookieStore;
        setHeader();
        newThreadStart(httpEntity.getUrl(), httpClient, parseString);
    }


    private void newThreadStart(String url, HttpClient httpClient, ParseString parseString) {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> urls;
        parseString.pretreatment(url,httpGet);
        System.out.println("url:  " + httpGet.getURI().toString());
        try {
            CloseableHttpResponse execute = (CloseableHttpResponse) httpClient.execute(httpGet);
            urls = parseString.doParse(execute,cookieStore);
            execute.close();
            urls.forEach(s -> newThreadStart(s, httpClient, parseString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        Optional.ofNullable(httpEntity.getHeader()).ifPresent(m -> m.forEach(httpGet::setHeader));
    }
}
