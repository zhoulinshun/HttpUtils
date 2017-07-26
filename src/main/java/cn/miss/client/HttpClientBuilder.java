package cn.miss.client;


import cn.miss.entity.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpClientBuilder {

    private CloseableHttpClient httpClient;
    private BasicCookieStore cookieStore = new BasicCookieStore();
    private HttpClientInterface client;

    public void start(HttpEntity httpEntity){
        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        if(httpEntity.getMethod().equals("GET")){
            client = new HttpGetClient();
        }else {
            client = new HttpPostClient();
        }
        client.doStart(httpEntity,httpClient,cookieStore);
    }
}
