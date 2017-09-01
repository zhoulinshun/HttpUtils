package cn.miss.client;

import cn.miss.entity.HttpEntity;
import cn.miss.parse.ParseString;
import cn.miss.utils.ProjectFlag;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

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
    private CookieStore cookieStore;

    public void doStart(HttpEntity httpEntity, HttpClient httpClient, CookieStore cookieStore, ParseString parseString) {
        httpGet = new HttpGet();
        this.httpEntity = httpEntity;
        this.cookieStore = cookieStore;
        setHeader();
        httpGet.getConfig();
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000).build();
        httpGet.setConfig(config);
        parseString.start(httpEntity,httpGet);
        newThreadStart(httpEntity.getUrl(), httpClient, parseString);
        parseString.end();
    }


    private void newThreadStart(String url, HttpClient httpClient, ParseString parseString) {
        try {
            if (httpEntity.getSpeed() != 0)
                Thread.sleep(httpEntity.getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> urls;
        parseString.pretreatment(url, httpGet,cookieStore);
        parseString.append("url:  " + httpGet.getURI().toString());
        try {
            CloseableHttpResponse execute = (CloseableHttpResponse) httpClient.execute(httpGet);
            urls = parseString.doParse(execute);
            execute.close();
            for (String s : urls) {
                if (ProjectFlag.flag)
                    newThreadStart(s, httpClient, parseString);
                else
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        Optional.ofNullable(httpEntity.getHeader()).ifPresent(m -> m.forEach(httpGet::setHeader));
    }
}
