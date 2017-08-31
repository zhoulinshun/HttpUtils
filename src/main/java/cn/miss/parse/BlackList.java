package cn.miss.parse;

import cn.miss.entity.HttpEntity;
import cn.miss.utils.Utils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/7.
 */
public class BlackList extends ParseString {

    private int i = 1;
    private Connection connection = Utils.getConnection();

    @Override
    public List<String> doParse(CloseableHttpResponse response, CookieStore cookieStore) {


        return null;
    }

    private List<String> getId(String html){
        List<String> list = new ArrayList<>();


        return list;
    }


    @Override
    public void pretreatment(String url, HttpRequestBase client) {

    }

    @Override
    public void init(HttpEntity entity) {

    }
}
