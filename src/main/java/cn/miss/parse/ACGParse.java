package cn.miss.parse;

import cn.miss.entity.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/2.
 */
public class ACGParse extends ParseString {
    private String title;
    private String mouth;
    private String day;

    @Override
    public List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore) {
        Calendar calendar = new GregorianCalendar();



        return null;
    }

    @Override
    public void pretreatment(String url, HttpRequestBase client) {

    }

    @Override
    public void init(HttpEntity entity) {

    }
}
