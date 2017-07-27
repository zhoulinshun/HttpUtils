package cn.miss.client;

import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpGetMethodClient implements HttpMethodClient {


    private HttpGet httpGet;
    private HttpEntity httpEntity;


    public void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore, ParseString parseString) {
        httpGet = new HttpGet();
        this.httpEntity = httpEntity;
        setHeader();
        newThreadStart(parseString.pretreatment(httpEntity.getUrl()), httpClient, parseString);
    }


    private void newThreadStart(String url, HttpClient httpClient, ParseString parseString) {
        List<String> urls;
        httpGet.setURI(URI.create(url));
        System.out.println("url:  " + url);
        try {
            CloseableHttpResponse execute = (CloseableHttpResponse) httpClient.execute(httpGet);
            urls = parseString.doParse(execute.getEntity());
            execute.close();
            urls.forEach(s -> newThreadStart(s, httpClient, parseString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        Optional.ofNullable(httpEntity.getHeader()).ifPresent(m -> m.forEach(httpGet::setHeader));
    }
}
