package cn.miss.test;

import cn.miss.client.HttpClientBuilder;
import cn.miss.entity.HttpEntity;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/26.
 */
public class HttpTest {
    @Test
    public void test(){
        HttpClientBuilder builder = new HttpClientBuilder();
        HttpEntity httpEntity = new HttpEntity();
        HashMap<String, String> map = new HashMap<>();
        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding","gzip, deflate, br");
        map.put("Accept-Language","zh-CN,zh;q=0.8");
        map.put("Cache-Control","max-age=0");
        map.put("Connection","keep-alive");
//        map.put("Host","www.zhihu.com");
//        map.put("Referer","https://www.zhihu.com/topic/19550228/top-answers");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        httpEntity.setUrl("https://www.baidu.com");
        httpEntity.setMethod("GET");
        httpEntity.setHeader(map);
        builder.start(httpEntity);
    }
}
